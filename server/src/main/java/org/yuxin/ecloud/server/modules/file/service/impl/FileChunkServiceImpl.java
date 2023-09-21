package org.yuxin.ecloud.server.modules.file.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.yuxin.ecloud.core.exception.ECloudBusinessException;
import org.yuxin.ecloud.core.utils.IdUtil;
import org.yuxin.ecloud.lock.core.annotation.Lock;
import org.yuxin.ecloud.server.common.config.ECloudServerConfig;
import org.yuxin.ecloud.server.modules.file.context.FileChunkSaveContext;
import org.yuxin.ecloud.server.modules.file.converter.FileConverter;
import org.yuxin.ecloud.server.modules.file.entity.ECloudFileChunk;
import org.yuxin.ecloud.server.modules.file.enums.MergeFlagEnum;
import org.yuxin.ecloud.server.modules.file.mapper.ECloudFileChunkMapper;
import org.yuxin.ecloud.server.modules.file.service.IFileChunkService;
import org.yuxin.ecloud.storage.engine.core.StorageEngine;
import org.yuxin.ecloud.storage.engine.core.context.StoreFileChunkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @author yuxin
 * @description 针对表【e_cloud_file_chunk(文件分片信息表)】的数据库操作Service实现
 * @createDate 2023-05-20 18:36:41
 */
@Service
public class FileChunkServiceImpl extends ServiceImpl<ECloudFileChunkMapper, ECloudFileChunk> implements IFileChunkService {

    @Autowired
    private ECloudServerConfig config;

    @Autowired
    private FileConverter fileConverter;

    @Autowired
    private StorageEngine storageEngine;

    /**
     * 文件分片保存
     * <p>
     * 1、保存文件分片和记录
     * 2、判断文件分片是否全部上传完成
     *
     * @param context
     */
    @Lock(name = "saveChunkFileLock", keys = {"#context.userId", "#context.identifier"}, expireSecond = 10L)
    @Override
    public void saveChunkFile(FileChunkSaveContext context) {
        doSaveChunkFile(context);
        doJudgeMergeFile(context);
    }

    /**
     * 判断是否所有的分片均没上传完成
     *
     * @param context
     */
    private void doJudgeMergeFile(FileChunkSaveContext context) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("identifier", context.getIdentifier());
        queryWrapper.eq("create_user", context.getUserId());
        int count = count(queryWrapper);
        if (count == context.getTotalChunks().intValue()) {
            context.setMergeFlagEnum(MergeFlagEnum.READY);
        }
    }

    /**
     * 执行文件分片上传保存的操作
     * <p>
     * 1、委托文件存储引擎存储文件分片
     * 2、保存文件分片记录
     *
     * @param context
     */
    private void doSaveChunkFile(FileChunkSaveContext context) {
        doStoreFileChunk(context);
        doSaveRecord(context);
    }

    /**
     * 保存文件分片记录
     *
     * @param context
     */
    private void doSaveRecord(FileChunkSaveContext context) {
        ECloudFileChunk record = new ECloudFileChunk();
        record.setId(IdUtil.get());
        record.setIdentifier(context.getIdentifier());
        record.setRealPath(context.getRealPath());
        record.setChunkNumber(context.getChunkNumber());
        record.setExpirationTime(DateUtil.offsetDay(new Date(), config.getChunkFileExpirationDays()));
        record.setCreateUser(context.getUserId());
        record.setCreateTime(new Date());
        if (!save(record)) {
            throw new ECloudBusinessException("文件分片上传失败");
        }
    }

    /**
     * 委托文件存储引擎保存文件分片
     *
     * @param context
     */
    private void doStoreFileChunk(FileChunkSaveContext context) {
        try {
            StoreFileChunkContext storeFileChunkContext = fileConverter.fileChunkSaveContext2StoreFileChunkContext(context);
            storeFileChunkContext.setInputStream(context.getFile().getInputStream());
            storageEngine.storeChunk(storeFileChunkContext);
            context.setRealPath(storeFileChunkContext.getRealPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ECloudBusinessException("文件分片上传失败");
        }
    }

}




