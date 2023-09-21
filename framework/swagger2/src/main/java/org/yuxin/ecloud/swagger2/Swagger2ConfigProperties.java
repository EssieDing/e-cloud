package org.yuxin.ecloud.swagger2;

import org.yuxin.ecloud.core.constants.ECloudConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * swagger2配置属性实体
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger2")
public class Swagger2ConfigProperties {

    private boolean show = true;

    private String groupName = "e-cloud";

    private String basePackage = ECloudConstants.BASE_COMPONENT_SCAN_PATH;

    private String title = "e-cloud-server";

    private String description = "e-cloud-server";

    private String termsOfServiceUrl = "http://127.0.0.1:${server.port}";

    private String contactName = "rubin";

    private String contactUrl = "https://blog.rubinchu.com";

    private String contactEmail = "rubinchu@126.com";

    private String version = "1.0";

}
