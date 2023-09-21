package org.yuxin.ecloud.server.common.stream.consumer;

import org.yuxin.ecloud.server.common.stream.channel.ECloudChannels;
import org.yuxin.ecloud.server.common.stream.event.TestEvent;
import org.yuxin.ecloud.stream.core.AbstractConsumer;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 测试消息消费者
 */
@Component
public class TestConsumer extends AbstractConsumer {

    /**
     * 消费测试消息
     *
     * @param message
     */
    @StreamListener(ECloudChannels.TEST_INPUT)
    public void consumeTestMessage(Message<TestEvent> message) {
        printLog(message);
    }

}
