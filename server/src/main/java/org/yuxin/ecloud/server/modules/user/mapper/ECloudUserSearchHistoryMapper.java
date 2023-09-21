package org.yuxin.ecloud.server.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.yuxin.ecloud.server.modules.user.context.QueryUserSearchHistoryContext;
import org.yuxin.ecloud.server.modules.user.entity.ECloudUserSearchHistory;
import org.yuxin.ecloud.server.modules.user.vo.UserSearchHistoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yuxin
 * @description 针对表【e_cloud_user_search_history(用户搜索历史表)】的数据库操作Mapper
 * @createDate 2023-05-20 18:34:37
 * @Entity org.yuxin.ecloud.server.modules.user.entity.ECloudUserSearchHistory
 */
public interface ECloudUserSearchHistoryMapper extends BaseMapper<ECloudUserSearchHistory> {

    /**
     * 查询用户的最近十条搜索历史记录
     *
     * @param context
     * @return
     */
    List<UserSearchHistoryVO> selectUserSearchHistories(@Param("param") QueryUserSearchHistoryContext context);

}




