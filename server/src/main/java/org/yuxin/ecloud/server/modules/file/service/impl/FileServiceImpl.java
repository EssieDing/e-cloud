package org.yuxin.ecloud.server.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.yuxin.ecloud.core.exception.ECloudBusinessException;
import org.yuxin.ecloud.core.utils.FileUtils;
import org.yuxin.ecloud.core.utils.IdUtil;
import org.yuxin.ecloud.server.common.stream.channel.ECloudChannels;
import org.yuxin.ecloud.server.common.stream.event.log.ErrorLogEvent;
import org.yuxin.ecloud.server.modules.file.context.FileChunkMergeAndSaveContext;
import org.yuxin.ecloud.server.modules.file.context.FileSaveContext;
import org.yuxin.ecloud.server.modules.file.context.QueryRealFileListContext;
import org.yuxin.ecloud.server.modules.file.entity.ECloudFile;
import org.yuxin.ecloud.server.modules.file.entity.ECloudFileChunk;
import org.yuxin.ecloud.server.modules.file.mapper.ECloudFileMapper;
import org.yuxin.ecloud.server.modules.file.service.IFileChunkService;
import org.yuxin.ecloud.server.modules.file.service.IFileService;
import org.yuxin.ecloud.storage.engine.core.StorageEngine;
import org.yuxin.ecloud.storage.engine.core.context.DeleteFileContext;
import org.yuxin.ecloud.storage.engine.core.context.MergeFileContext;
import org.yuxin.ecloud.storage.engine.core.context.StoreFileContext;
import org.yuxin.ecloud.stream.core.IStreamProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yuxin
 * @description 针对表【e_cloud_file(物理文件信息表)】的数据库操作Service实现
 * @createDate 2023-05-20 18:36:41
 */
@Service
public class FileServiceImpl extends ServiceImpl<ECloudFileMapper, ECloudFile> implements IFileService {

    @Autowired
    private StorageEngine storageEngine;

    @Autowired
    @Qualifier(value = "defaultStreamProducer")
    private IStreamProducer producer;

    @Autowired
    private IFileChunkService iFileChunkService;

    /**
     * 根据条件查询用户的实际文件列表
     *
     * @param context
     * @return
     */
    @Override
    public List<ECloudFile> getFileList(QueryRealFileListContext context) {
        Long userId = context.getUserId();
        String identifier = context.getIdentifier();
        LambdaQueryWrapper<ECloudFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(userId), ECloudFile::getCreateUser, userId);
        queryWrapper.eq(StringUtils.isNotBlank(identifier), ECloudFile::getIdentifier, identifier);
        return list(queryWrapper);
    }

    /**
     * 上传单文件并保存实体记录
     * <p>
     * 1、上传单文件
     * 2、保存实体记录
     *
     * @param context
     */
    @Override
    public void saveFile(FileSaveContext context) {
        storeMultipartFile(context);
        ECloudFile record = doSaveFile(context.getFilename(),
                context.getRealPath(),
                context.getTotalSize(),
                context.getIdentifier(),
                context.getUserId());
        context.setRecord(record);
    }

    /**
     * 合并物理文件并保存物理文件记录
     * <p>
     * 1、委托文件存储引擎合并文件分片
     * 2、保存物理文件记录
     *
     * @param context
     */
    @Override
    public void mergeFileChunkAndSaveFile(FileChunkMergeAndSaveContext context) {
        doMergeFileChunk(context);
        ECloudFile record = doSaveFile(context.getFilename(), context.getRealPath(), context.getTotalSize(), context.getIdentifier(), context.getUserId());
        context.setRecord(record);
    }

    /************************************************private************************************************/

    /**
     * 委托文件存储引擎合并文件分片
     * <p>
     * 1、查询文件分片的记录
     * 2、根据文件分片的记录去合并物理文件
     * 3、删除文件分片记录
     * 4、封装合并文件的真实存储路径到上下文信息中
     *
     * @param context
     */
    private void doMergeFileChunk(FileChunkMergeAndSaveContext context) {
        QueryWrapper<ECloudFileChunk> queryWrapper = Wrappers.query();
        queryWrapper.eq("identifier", context.getIdentifier());
        queryWrapper.eq("create_user", context.getUserId());
        queryWrapper.ge("expiration_time", new Date());
        List<ECloudFileChunk> chunkRecoredList = iFileChunkService.list(queryWrapper);
        if (CollectionUtils.isEmpty(chunkRecoredList)) {
            throw new ECloudBusinessException("该文件未找到分片记录");
        }
        List<String> realPathList = chunkRecoredList.stream()
                .sorted(Comparator.comparing(ECloudFileChunk::getChunkNumber))
                .map(ECloudFileChunk::getRealPath)
                .collect(Collectors.toList());

        try {
            MergeFileContext mergeFileContext = new MergeFileContext();
            mergeFileContext.setFilename(context.getFilename());
            mergeFileContext.setIdentifier(context.getIdentifier());
            mergeFileContext.setUserId(context.getUserId());
            mergeFileContext.setRealPathList(realPathList);
            storageEngine.mergeFile(mergeFileContext);
            context.setRealPath(mergeFileContext.getRealPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ECloudBusinessException("文件分片合并失败");
        }

        List<Long> fileChunkRecordIdList = chunkRecoredList.stream().map(ECloudFileChunk::getId).collect(Collectors.toList());
        iFileChunkService.removeByIds(fileChunkRecordIdList);
    }

    /**
     * 保存实体文件记录
     *
     * @param filename
     * @param realPath
     * @param totalSize
     * @param identifier
     * @param userId
     * @return
     */
    private ECloudFile doSaveFile(String filename, String realPath, Long totalSize, String identifier, Long userId) {
        ECloudFile record = assembleECloudFile(filename, realPath, totalSize, identifier, userId);
        if (!save(record)) {
            try {
                DeleteFileContext deleteFileContext = new DeleteFileContext();
                deleteFileContext.setRealFilePathList(Lists.newArrayList(realPath));
                storageEngine.delete(deleteFileContext);
            } catch (IOException e) {
                e.printStackTrace();
                ErrorLogEvent errorLogEvent = new ErrorLogEvent("文件物理删除失败，请执行手动删除！文件路径: " + realPath, userId);
                producer.sendMessage(ECloudChannels.ERROR_LOG_OUTPUT, errorLogEvent);
            }
        }
        return record;
    }

    /**
     * 拼装文件实体对象
     *
     * @param filename
     * @param realPath
     * @param totalSize
     * @param identifier
     * @param userId
     * @return
     */
    private ECloudFile assembleECloudFile(String filename, String realPath, Long totalSize, String identifier, Long userId) {
        ECloudFile record = new ECloudFile();

        record.setFileId(IdUtil.get());
        record.setFilename(filename);
        record.setRealPath(realPath);
        record.setFileSize(String.valueOf(totalSize));
        record.setFileSizeDesc(FileUtils.byteCountToDisplaySize(totalSize));
        record.setFileSuffix(FileUtils.getFileSuffix(filename));
        record.setFilePreviewContentType(FileUtils.getContentType(realPath));
        record.setIdentifier(identifier);
        record.setCreateUser(userId);
        record.setCreateTime(new Date());

        return record;
    }

    /**
     * 上传单文件
     * 该方法委托文件存储引擎实现
     *
     * @param context
     */
    private void storeMultipartFile(FileSaveContext context) {
        try {
            StoreFileContext storeFileContext = new StoreFileContext();
            storeFileContext.setInputStream(context.getFile().getInputStream());
            storeFileContext.setFilename(context.getFilename());
            storeFileContext.setTotalSize(context.getTotalSize());
            storageEngine.store(storeFileContext);
            context.setRealPath(storeFileContext.getRealPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ECloudBusinessException("文件上传失败");
        }
    }


}




