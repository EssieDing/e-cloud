package org.yuxin.ecloud.server.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.yuxin.ecloud.server.modules.user.context.QueryUserSearchHistoryContext;
import org.yuxin.ecloud.server.modules.user.entity.ECloudUserSearchHistory;
import org.yuxin.ecloud.server.modules.user.mapper.ECloudUserSearchHistoryMapper;
import org.yuxin.ecloud.server.modules.user.service.IUserSearchHistoryService;
import org.yuxin.ecloud.server.modules.user.vo.UserSearchHistoryVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuxin
 * @description 针对表【e_cloud_user_search_history(用户搜索历史表)】的数据库操作Service实现
 * @createDate 2023-05-20 18:34:37
 */
@Service(value = "userSearchHistoryService")
public class UserSearchHistoryServiceImpl extends ServiceImpl<ECloudUserSearchHistoryMapper, ECloudUserSearchHistory> implements IUserSearchHistoryService {

    /**
     * 查询用户的搜索历史记录，默认十条
     *
     * @param context
     * @return
     */
    @Override
    public List<UserSearchHistoryVO> getUserSearchHistories(QueryUserSearchHistoryContext context) {
        return baseMapper.selectUserSearchHistories(context);
    }
}




