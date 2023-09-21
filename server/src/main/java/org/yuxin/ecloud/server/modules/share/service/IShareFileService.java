package org.yuxin.ecloud.server.modules.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.yuxin.ecloud.server.modules.share.context.SaveShareFilesContext;
import org.yuxin.ecloud.server.modules.share.entity.ECloudShareFile;

/**
 * @author yuxin
 * @description 针对表【e_cloud_share_file(用户分享文件表)】的数据库操作Service
 * @createDate 2023-05-20 18:38:38
 */
public interface IShareFileService extends IService<ECloudShareFile> {

    /**
     * 保存分享的文件的对应关系
     *
     * @param context
     */
    void saveShareFiles(SaveShareFilesContext context);

}
