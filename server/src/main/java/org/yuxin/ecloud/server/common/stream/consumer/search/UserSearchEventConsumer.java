package org.yuxin.ecloud.server.common.stream.consumer.search;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.yuxin.ecloud.core.utils.IdUtil;
import org.yuxin.ecloud.server.common.stream.channel.ECloudChannels;
import org.yuxin.ecloud.server.common.stream.event.search.UserSearchEvent;
import org.yuxin.ecloud.server.modules.user.entity.ECloudUserSearchHistory;
import org.yuxin.ecloud.server.modules.user.service.IUserSearchHistoryService;
import org.yuxin.ecloud.stream.core.AbstractConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 用户搜索事件监听器
 */
@Component
public class UserSearchEventConsumer extends AbstractConsumer {

    @Autowired
    private IUserSearchHistoryService iUserSearchHistoryService;

    /**
     * 监听用户搜索事件，将其保存到用户的搜索历史记录当中
     *
     * @param message
     */
    @StreamListener(ECloudChannels.USER_SEARCH_INPUT)
    public void saveSearchHistory(Message<UserSearchEvent> message) {
        if (isEmptyMessage(message)) {
            return;
        }
        printLog(message);
        UserSearchEvent event = message.getPayload();
        ECloudUserSearchHistory record = new ECloudUserSearchHistory();

        record.setId(IdUtil.get());
        record.setUserId(event.getUserId());
        record.setSearchContent(event.getKeyword());
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());

        try {
            iUserSearchHistoryService.save(record);
        } catch (DuplicateKeyException e) {
            UpdateWrapper updateWrapper = Wrappers.update();
            updateWrapper.eq("user_id", event.getUserId());
            updateWrapper.eq("search_content", event.getKeyword());
            updateWrapper.set("update_time", new Date());
            iUserSearchHistoryService.update(updateWrapper);
        }

    }

}
