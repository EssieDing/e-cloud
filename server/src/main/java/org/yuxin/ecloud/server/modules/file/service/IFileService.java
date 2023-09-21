package org.yuxin.ecloud.server.modules.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.yuxin.ecloud.server.modules.file.context.FileChunkMergeAndSaveContext;
import org.yuxin.ecloud.server.modules.file.context.FileSaveContext;
import org.yuxin.ecloud.server.modules.file.context.QueryRealFileListContext;
import org.yuxin.ecloud.server.modules.file.entity.ECloudFile;

import java.util.List;

/**
 * @author yuxin
 * @description 针对表【e_cloud_file(物理文件信息表)】的数据库操作Service
 * @createDate 2023-05-20 18:36:41
 */
public interface IFileService extends IService<ECloudFile> {

    /**
     * 根据条件查询用户的实际文件列表
     *
     * @param context
     * @return
     */
    List<ECloudFile> getFileList(QueryRealFileListContext context);

    /**
     * 上传单文件并保存实体记录
     *
     * @param context
     */
    void saveFile(FileSaveContext context);

    /**
     * 合并物理文件并保存物理文件记录
     *
     * @param context
     */
    void mergeFileChunkAndSaveFile(FileChunkMergeAndSaveContext context);

}
