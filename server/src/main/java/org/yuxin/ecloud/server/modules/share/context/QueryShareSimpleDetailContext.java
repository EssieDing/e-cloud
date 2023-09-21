package org.yuxin.ecloud.server.modules.share.context;

import org.yuxin.ecloud.server.modules.share.entity.ECloudShare;
import org.yuxin.ecloud.server.modules.share.vo.ShareSimpleDetailVO;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询分享简单详情上下文实体信息
 */
@Data
public class QueryShareSimpleDetailContext implements Serializable {

    /**
     * 分享的ID
     */
    private Long shareId;

    /**
     * 分享对应的实体信息
     */
    private ECloudShare record;

    /**
     * 简单分享详情的VO对象
     */
    private ShareSimpleDetailVO vo;

}
