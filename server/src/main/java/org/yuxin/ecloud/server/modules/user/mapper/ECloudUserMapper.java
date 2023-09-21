package org.yuxin.ecloud.server.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.yuxin.ecloud.server.modules.user.entity.ECloudUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author yuxin
 * @description 针对表【e_cloud_user(用户信息表)】的数据库操作Mapper
 * @createDate 2023-05-20 18:34:37
 * @Entity org.yuxin.ecloud.server.modules.user.entity.ECloudUser
 */
public interface ECloudUserMapper extends BaseMapper<ECloudUser> {

    /**
     * 通过用户名称查询用户设置的密保问题
     *
     * @param username
     * @return
     */
    String selectQuestionByUsername(@Param("username") String username);

}




