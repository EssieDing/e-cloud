package org.yuxin.ecloud.server.modules.share.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.yuxin.ecloud.server.modules.file.vo.ECloudUserFileVO;
import org.yuxin.ecloud.server.modules.share.context.*;
import org.yuxin.ecloud.server.modules.share.entity.ECloudShare;
import org.yuxin.ecloud.server.modules.share.vo.ECloudShareUrlListVO;
import org.yuxin.ecloud.server.modules.share.vo.ECloudShareUrlVO;
import org.yuxin.ecloud.server.modules.share.vo.ShareDetailVO;
import org.yuxin.ecloud.server.modules.share.vo.ShareSimpleDetailVO;

import java.util.List;

/**
 * @author yuxin
 * @description 针对表【e_cloud_share(用户分享表)】的数据库操作Service
 * @createDate 2023-05-20 18:38:38
 */
public interface IShareService extends IService<ECloudShare> {

    /**
     * 创建分享链接
     *
     * @param context
     * @return
     */
    ECloudShareUrlVO create(CreateShareUrlContext context);

    /**
     * 查询用户的分享列表
     *
     * @param context
     * @return
     */
    List<ECloudShareUrlListVO> getShares(QueryShareListContext context);

    /**
     * 取消分享链接
     *
     * @param context
     */
    void cancelShare(CancelShareContext context);

    /**
     * 校验分享码
     *
     * @param context
     * @return
     */
    String checkShareCode(CheckShareCodeContext context);

    /**
     * 查询分享的详情
     *
     * @param context
     * @return
     */
    ShareDetailVO detail(QueryShareDetailContext context);

    /**
     * 查询分享的简单详情
     *
     * @param context
     * @return
     */
    ShareSimpleDetailVO simpleDetail(QueryShareSimpleDetailContext context);

    /**
     * 获取下一级的文件列表
     *
     * @param context
     * @return
     */
    List<ECloudUserFileVO> fileList(QueryChildFileListContext context);

    /**
     * 转存至我的网盘
     *
     * @param context
     */
    void saveFiles(ShareSaveContext context);

    /**
     * 分享的文件下载
     *
     * @param context
     */
    void download(ShareFileDownloadContext context);

    /**
     * 刷新受影响的对应的分享的状态
     *
     * @param allAvailableFileIdList
     */
    void refreshShareStatus(List<Long> allAvailableFileIdList);

    /**
     * 滚动查询已存在的分享ID
     *
     * @param startId
     * @param limit
     * @return
     */
    List<Long> rollingQueryShareId(long startId, long limit);

}
