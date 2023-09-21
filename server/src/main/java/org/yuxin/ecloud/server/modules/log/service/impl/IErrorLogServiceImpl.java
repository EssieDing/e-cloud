package org.yuxin.ecloud.server.modules.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.yuxin.ecloud.server.modules.log.entity.ECloudErrorLog;
import org.yuxin.ecloud.server.modules.log.mapper.ECloudErrorLogMapper;
import org.yuxin.ecloud.server.modules.log.service.IErrorLogService;
import org.springframework.stereotype.Service;

/**
 * @author yuxin
 * @description 针对表【e_cloud_error_log(错误日志表)】的数据库操作Service实现
 * @createDate 2023-05-20 18:37:48
 */
@Service
public class IErrorLogServiceImpl extends ServiceImpl<ECloudErrorLogMapper, ECloudErrorLog>
        implements IErrorLogService {

}




