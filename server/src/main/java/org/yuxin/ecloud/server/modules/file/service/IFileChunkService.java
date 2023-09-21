package org.yuxin.ecloud.server.modules.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.yuxin.ecloud.server.modules.file.context.FileChunkSaveContext;
import org.yuxin.ecloud.server.modules.file.entity.ECloudFileChunk;

/**
 * @author yuxin
 * @description 针对表【e_cloud_file_chunk(文件分片信息表)】的数据库操作Service
 * @createDate 2023-05-20 18:36:41
 */
public interface IFileChunkService extends IService<ECloudFileChunk> {

    /**
     * 文件分片保存
     *
     * @param context
     */
    void saveChunkFile(FileChunkSaveContext context);

}
