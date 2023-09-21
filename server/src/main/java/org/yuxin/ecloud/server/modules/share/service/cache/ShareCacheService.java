package org.yuxin.ecloud.server.modules.share.service.cache;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.yuxin.ecloud.server.common.cache.AbstractManualCacheService;
import org.yuxin.ecloud.server.modules.share.entity.ECloudShare;
import org.yuxin.ecloud.server.modules.share.mapper.ECloudShareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 手动缓存实现分享业务的查询等操作
 */
@Component(value = "shareManualCacheService")
public class ShareCacheService extends AbstractManualCacheService<ECloudShare> {

    @Autowired
    private ECloudShareMapper mapper;

    @Override
    protected BaseMapper<ECloudShare> getBaseMapper() {
        return mapper;
    }

    /**
     * 获取缓存key的模版信息
     *
     * @return
     */
    @Override
    public String getKeyFormat() {
        return "SHARE:ID:%s";
    }

}
