package org.yuxin.ecloud.lock.zookeeper;

import org.yuxin.ecloud.lock.core.LockConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "org.yuxin.ecloud.lock.zookeeper")
public class ZooKeeperLockProperties {

    /**
     * zk链接地址，多个使用逗号隔开
     */
    private String host = "127.0.0.1:2181";

    /**
     * zk分布式锁的根路径
     */
    private String rootPath = LockConstants.E_CLOUD_LOCK_PATH;

}
