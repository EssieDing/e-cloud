package org.yuxin.ecloud.server.modules.share.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.yuxin.ecloud.core.exception.ECloudBusinessException;
import org.yuxin.ecloud.core.utils.IdUtil;
import org.yuxin.ecloud.server.modules.share.context.SaveShareFilesContext;
import org.yuxin.ecloud.server.modules.share.entity.ECloudShareFile;
import org.yuxin.ecloud.server.modules.share.mapper.ECloudShareFileMapper;
import org.yuxin.ecloud.server.modules.share.service.IShareFileService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yuxin
 * @description 针对表【e_cloud_share_file(用户分享文件表)】的数据库操作Service实现
 * @createDate 2023-05-20 18:38:38
 */
@Service
public class ShareFileServiceImpl extends ServiceImpl<ECloudShareFileMapper, ECloudShareFile> implements IShareFileService {

    /**
     * 保存分享的文件的对应关系
     *
     * @param context
     */
    @Override
    public void saveShareFiles(SaveShareFilesContext context) {
        Long shareId = context.getShareId();
        List<Long> shareFileIdList = context.getShareFileIdList();
        Long userId = context.getUserId();

        List<ECloudShareFile> records = Lists.newArrayList();

        for (Long shareFileId : shareFileIdList) {
            ECloudShareFile record = new ECloudShareFile();
            record.setId(IdUtil.get());
            record.setShareId(shareId);
            record.setFileId(shareFileId);
            record.setCreateUser(userId);
            record.setCreateTime(new Date());
            records.add(record);
        }

        if (!saveBatch(records)) {
            throw new ECloudBusinessException("保存文件分享关联关系失败");
        }
    }

}




