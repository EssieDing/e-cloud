package org.yuxin.ecloud.server.modules.recycle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.yuxin.ecloud.core.constants.ECloudConstants;
import org.yuxin.ecloud.core.exception.ECloudBusinessException;
import org.yuxin.ecloud.server.common.stream.channel.ECloudChannels;
import org.yuxin.ecloud.server.common.stream.event.file.FilePhysicalDeleteEvent;
import org.yuxin.ecloud.server.common.stream.event.file.FileRestoreEvent;
import org.yuxin.ecloud.server.modules.file.context.QueryFileListContext;
import org.yuxin.ecloud.server.modules.file.entity.ECloudUserFile;
import org.yuxin.ecloud.server.modules.file.enums.DelFlagEnum;
import org.yuxin.ecloud.server.modules.file.service.IUserFileService;
import org.yuxin.ecloud.server.modules.file.vo.ECloudUserFileVO;
import org.yuxin.ecloud.server.modules.recycle.context.DeleteContext;
import org.yuxin.ecloud.server.modules.recycle.context.QueryRecycleFileListContext;
import org.yuxin.ecloud.server.modules.recycle.context.RestoreContext;
import org.yuxin.ecloud.server.modules.recycle.service.IRecycleService;
import org.yuxin.ecloud.stream.core.IStreamProducer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 回收站模块业务处理类
 */
@Service
public class RecycleServiceImpl implements IRecycleService {

    @Autowired
    private IUserFileService iUserFileService;

    @Autowired
    @Qualifier(value = "defaultStreamProducer")
    private IStreamProducer producer;

    /**
     * 查询用户的回收站文件列表
     *
     * @param context
     * @return
     */
    @Override
    public List<ECloudUserFileVO> recycles(QueryRecycleFileListContext context) {
        QueryFileListContext queryFileListContext = new QueryFileListContext();
        queryFileListContext.setUserId(context.getUserId());
        queryFileListContext.setDelFlag(DelFlagEnum.YES.getCode());
        return iUserFileService.getFileList(queryFileListContext);
    }

    /**
     * 文件还原
     * <p>
     * 1、检查操作权限
     * 2、检查是不是可以还原
     * 3、执行文件还原的操作
     * 4、执行文件还原的后置操作
     *
     * @param context
     */
    @Override
    public void restore(RestoreContext context) {
        checkRestorePermission(context);
        checkRestoreFilename(context);
        doRestore(context);
        afterRestore(context);
    }

    /**
     * 文件彻底删除
     * <p>
     * 1、校验操作权限
     * 2、递归查找所有子文件
     * 3、执行文件删除的动作
     * 4、删除后的后置动作
     *
     * @param context
     */
    @Override
    public void delete(DeleteContext context) {
        checkFileDeletePermission(context);
        findAllFileRecords(context);
        doDelete(context);
        afterDelete(context);
    }

    /*************************************************private*************************************************/

    /**
     * 文件彻底删除之后的后置函数
     * <p>
     * 1、发送一个文件彻底删除的事件
     *
     * @param context
     */
    private void afterDelete(DeleteContext context) {
        FilePhysicalDeleteEvent event = new FilePhysicalDeleteEvent(context.getAllRecords());
        producer.sendMessage(ECloudChannels.PHYSICAL_DELETE_FILE_OUTPUT, event);
    }

    /**
     * 执行文件删除的动作
     *
     * @param context
     */
    private void doDelete(DeleteContext context) {
        List<ECloudUserFile> allRecords = context.getAllRecords();
        List<Long> fileIdList = allRecords.stream().map(ECloudUserFile::getFileId).collect(Collectors.toList());
        if (!iUserFileService.removeByIds(fileIdList)) {
            throw new ECloudBusinessException("文件删除失败");
        }
    }

    /**
     * 递归查询所有的子文件
     *
     * @param context
     */
    private void findAllFileRecords(DeleteContext context) {
        List<ECloudUserFile> records = context.getRecords();
        List<ECloudUserFile> allRecords = iUserFileService.findAllFileRecords(records);
        context.setAllRecords(allRecords);
    }

    /**
     * 校验文件删除的操作权限
     *
     * @param context
     */
    private void checkFileDeletePermission(DeleteContext context) {
        QueryWrapper queryWrapper = Wrappers.query();
        queryWrapper.eq("user_id", context.getUserId());
        queryWrapper.in("file_id", context.getFileIdList());
        List<ECloudUserFile> records = iUserFileService.list(queryWrapper);
        if (CollectionUtils.isEmpty(records) || records.size() != context.getFileIdList().size()) {
            throw new ECloudBusinessException("您无权删除该文件");
        }
        context.setRecords(records);
    }

    /**
     * 文件还原的后置操作
     * <p>
     * 1、发布文件还原事件
     *
     * @param context
     */
    private void afterRestore(RestoreContext context) {
        FileRestoreEvent event = new FileRestoreEvent(context.getFileIdList());
        producer.sendMessage(ECloudChannels.FILE_RESTORE_OUTPUT, event);
    }

    /**
     * 执行文件还原的动作
     *
     * @param context
     */
    private void doRestore(RestoreContext context) {
        List<ECloudUserFile> records = context.getRecords();
        records.stream().forEach(record -> {
            record.setDelFlag(DelFlagEnum.NO.getCode());
            record.setUpdateUser(context.getUserId());
            record.setUpdateTime(new Date());
        });
        boolean updateFlag = iUserFileService.updateBatchById(records);
        if (!updateFlag) {
            throw new ECloudBusinessException("文件还原失败");
        }
    }

    /**
     * 检查要还原的文件名称是不是被占用
     * <p>
     * 1、要还原的文件列表中有同一个文件夹下面相同名称的文件 不允许还原
     * 2、要还原的文件当前的父文件夹下面存在同名文件，我们不允许还原
     *
     * @param context
     */
    private void checkRestoreFilename(RestoreContext context) {
        List<ECloudUserFile> records = context.getRecords();

        Set<String> filenameSet = records.stream().map(record -> record.getFilename() + ECloudConstants.COMMON_SEPARATOR + record.getParentId()).collect(Collectors.toSet());
        if (filenameSet.size() != records.size()) {
            throw new ECloudBusinessException("文件还原失败，该还原文件中存在同名文件，请逐个还原并重命名");
        }

        for (ECloudUserFile record : records) {
            QueryWrapper queryWrapper = Wrappers.query();
            queryWrapper.eq("user_id", context.getUserId());
            queryWrapper.eq("parent_id", record.getParentId());
            queryWrapper.eq("filename", record.getFilename());
            queryWrapper.eq("del_flag", DelFlagEnum.NO.getCode());
            if (iUserFileService.count(queryWrapper) > 0) {
                throw new ECloudBusinessException("文件: " + record.getFilename() + " 还原失败，该文件夹下面已经存在了相同名称的文件或者文件夹，请重命名之后再执行文件还原操作");
            }
        }
    }

    /**
     * 检查文件还原的操作权限
     *
     * @param context
     */
    private void checkRestorePermission(RestoreContext context) {
        List<Long> fileIdList = context.getFileIdList();
        List<ECloudUserFile> records = iUserFileService.listByIds(fileIdList);
        if (CollectionUtils.isEmpty(records)) {
            throw new ECloudBusinessException("文件还原失败");
        }
        Set<Long> userIdSet = records.stream().map(ECloudUserFile::getUserId).collect(Collectors.toSet());
        if (userIdSet.size() > 1) {
            throw new ECloudBusinessException("您无权执行文件还原");
        }

        if (!userIdSet.contains(context.getUserId())) {
            throw new ECloudBusinessException("您无权执行文件还原");
        }
        context.setRecords(records);
    }

}
