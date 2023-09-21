package org.yuxin.ecloud.server.modules.share.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.yuxin.ecloud.server.modules.share.entity.ECloudShare;
import org.yuxin.ecloud.server.modules.share.vo.ECloudShareUrlListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yuxin
 * @description 针对表【e_cloud_share(用户分享表)】的数据库操作Mapper
 * @createDate 2023-05-20 18:38:38
 * @Entity org.yuxin.ecloud.server.modules.share.entity.ECloudShare
 */
public interface ECloudShareMapper extends BaseMapper<ECloudShare> {

    /**
     * 查询用户的分享列表
     *
     * @param userId
     * @return
     */
    List<ECloudShareUrlListVO> selectShareVOListByUserId(@Param("userId") Long userId);

    /**
     * 滚动查询已存在的分享ID集合
     *
     * @param startId
     * @param limit
     * @return
     */
    List<Long> rollingQueryShareId(@Param("startId") long startId, @Param("limit") long limit);

}




