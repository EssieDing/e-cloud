//package org.yuxin.ecloud.server.common.listenner.file;
//
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import org.yuxin.ecloud.core.constants.ECloudConstants;
//import org.yuxin.ecloud.server.common.event.file.FilePhysicalDeleteEvent;
//import org.yuxin.ecloud.server.common.event.log.ErrorLogEvent;
//import org.yuxin.ecloud.server.modules.file.entity.ECloudFile;
//import org.yuxin.ecloud.server.modules.file.entity.ECloudUserFile;
//import org.yuxin.ecloud.server.modules.file.enums.FolderFlagEnum;
//import org.yuxin.ecloud.server.modules.file.service.IFileService;
//import org.yuxin.ecloud.server.modules.file.service.IUserFileService;
//import org.yuxin.ecloud.storage.engine.core.StorageEngine;
//import org.yuxin.ecloud.storage.engine.core.context.DeleteFileContext;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.event.EventListener;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * 文件物理删除监听器
// */
//@Component
//public class FilePhysicalDeleteEventListener implements ApplicationContextAware {
//
//    @Autowired
//    private IFileService iFileService;
//
//    @Autowired
//    private IUserFileService iUserFileService;
//
//    @Autowired
//    private StorageEngine storageEngine;
//
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//    /**
//     * 监听文件物理删除事件执行器
//     * <p>
//     * 该执行器是一个资源释放器，释放被物理删除的文件列表中关联的实体文件记录
//     * <p>
//     * 1、查询所有无引用的实体文件记录
//     * 2、删除记录
//     * 3、物理清理文件（委托文件存储引擎）
//     *
//     * @param event
//     */
//    @EventListener(classes = FilePhysicalDeleteEvent.class)
//    @Async(value = "eventListenerTaskExecutor")
//    public void physicalDeleteFile(FilePhysicalDeleteEvent event) {
//        List<ECloudUserFile> allRecords = event.getAllRecords();
//        if (CollectionUtils.isEmpty(allRecords)) {
//            return;
//        }
//        List<Long> realFileIdList = findAllUnusedRealFileIdList(allRecords);
//        if (CollectionUtils.isEmpty(realFileIdList)) {
//            return;
//        }
//        List<ECloudFile> realFileRecords = iFileService.listByIds(realFileIdList);
//        if (CollectionUtils.isEmpty(realFileRecords)) {
//            return;
//        }
//        if (!iFileService.removeByIds(realFileIdList)) {
//            applicationContext.publishEvent(new ErrorLogEvent(this, "实体文件记录：" + JSON.toJSONString(realFileIdList) + "， 物理删除失败，请执行手动删除", ECloudConstants.ZERO_LONG));
//            return;
//        }
//        physicalDeleteFileByStorageEngine(realFileRecords);
//    }
//
//    /*******************************************private*******************************************/
//
//    /**
//     * 委托文件存储引擎执行物理文件的删除
//     *
//     * @param realFileRecords
//     */
//    private void physicalDeleteFileByStorageEngine(List<ECloudFile> realFileRecords) {
//        List<String> realPathList = realFileRecords.stream().map(ECloudFile::getRealPath).collect(Collectors.toList());
//        DeleteFileContext deleteFileContext = new DeleteFileContext();
//        deleteFileContext.setRealFilePathList(realPathList);
//        try {
//            storageEngine.delete(deleteFileContext);
//        } catch (IOException e) {
//            applicationContext.publishEvent(new ErrorLogEvent(this, "实体文件：" + JSON.toJSONString(realPathList) + "， 物理删除失败，请执行手动删除", ECloudConstants.ZERO_LONG));
//        }
//    }
//
//    /**
//     * 查找所有没有被引用的真实文件记录ID集合
//     *
//     * @param allRecords
//     * @return
//     */
//    private List<Long> findAllUnusedRealFileIdList(List<ECloudUserFile> allRecords) {
//        List<Long> realFileIdList = allRecords.stream()
//                .filter(record -> Objects.equals(record.getFolderFlag(), FolderFlagEnum.NO.getCode()))
//                .filter(this::isUnused)
//                .map(ECloudUserFile::getRealFileId)
//                .collect(Collectors.toList());
//        return realFileIdList;
//    }
//
//    /**
//     * 校验文件的真实文件ID是不是没有被引用了
//     *
//     * @param record
//     * @return
//     */
//    private boolean isUnused(ECloudUserFile record) {
//        QueryWrapper queryWrapper = Wrappers.query();
//        queryWrapper.eq("real_file_id", record.getRealFileId());
//        return iUserFileService.count(queryWrapper) == ECloudConstants.ZERO_INT.intValue();
//    }
//
//
//}
