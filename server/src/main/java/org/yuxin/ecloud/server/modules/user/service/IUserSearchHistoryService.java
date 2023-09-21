package org.yuxin.ecloud.server.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.yuxin.ecloud.server.modules.user.context.QueryUserSearchHistoryContext;
import org.yuxin.ecloud.server.modules.user.entity.ECloudUserSearchHistory;
import org.yuxin.ecloud.server.modules.user.vo.UserSearchHistoryVO;

import java.util.List;

/**
 * @author yuxin
 * @description 针对表【e_cloud_user_search_history(用户搜索历史表)】的数据库操作Service
 * @createDate 2023-05-20 18:34:37
 */
public interface IUserSearchHistoryService extends IService<ECloudUserSearchHistory> {

    /**
     * 查询用户的搜索历史记录，默认十条
     *
     * @param context
     * @return
     */
    List<UserSearchHistoryVO> getUserSearchHistories(QueryUserSearchHistoryContext context);

}
