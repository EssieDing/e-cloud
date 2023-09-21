package org.yuxin.ecloud.server.common.config;

import org.yuxin.ecloud.core.constants.ECloudConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "org.yuxin.ecloud.server")
@Data
public class ECloudServerConfig {

    /**
     * 文件分片的过期天数
     */
    private Integer chunkFileExpirationDays = ECloudConstants.ONE_INT;

    /**
     * 分享链接的前缀
     */
    private String sharePrefix = "http://127.0.0.1:8080/share/";

}
