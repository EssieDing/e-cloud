package org.yuxin.ecloud.server.modules.test.controller;

import org.yuxin.ecloud.core.response.R;
import org.yuxin.ecloud.server.common.annotation.LoginIgnore;
import org.yuxin.ecloud.server.common.event.test.TestEvent;
import org.yuxin.ecloud.server.common.stream.channel.ECloudChannels;
import org.yuxin.ecloud.stream.core.IStreamProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试处理器
 */
@RestController
public class TestController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier(value = "defaultStreamProducer")
    private IStreamProducer producer;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 测试事件发布
     *
     * @return
     */
    @GetMapping("test")
    @LoginIgnore
    public R test() {
        applicationContext.publishEvent(new TestEvent(this, "test"));
        return R.success();
    }

    /**
     * 测试流事件发布
     *
     * @return
     */
    @GetMapping("stream/test")
    @LoginIgnore
    public R streamTest(String name) {
        org.yuxin.ecloud.server.common.stream.event.TestEvent testEvent = new org.yuxin.ecloud.server.common.stream.event.TestEvent();
        testEvent.setName(name);
        producer.sendMessage(ECloudChannels.TEST_OUTPUT, testEvent);
        return R.success();
    }

}
