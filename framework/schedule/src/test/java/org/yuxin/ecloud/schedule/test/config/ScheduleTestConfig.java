package org.yuxin.ecloud.schedule.test.config;

import org.yuxin.ecloud.core.constants.ECloudConstants;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 单元测试配置类
 */
@SpringBootConfiguration
@ComponentScan(ECloudConstants.BASE_COMPONENT_SCAN_PATH + ".schedule")
public class ScheduleTestConfig {
}
