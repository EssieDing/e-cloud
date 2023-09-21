package org.yuxin.ecloud.server.modules.user.service.cache;

import org.yuxin.ecloud.cache.core.constants.CacheConstants;
import org.yuxin.ecloud.server.common.cache.AnnotationCacheService;
import org.yuxin.ecloud.server.modules.user.entity.ECloudUser;
import org.yuxin.ecloud.server.modules.user.mapper.ECloudUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 用户模块缓存业务处理类
 */
@Component(value = "userAnnotationCacheService")
public class UserCacheService implements AnnotationCacheService<ECloudUser> {

    @Autowired
    private ECloudUserMapper mapper;

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    @Cacheable(cacheNames = CacheConstants.E_CLOUD_CACHE_NAME, keyGenerator = "userIdKeyGenerator", sync = true)
    @Override
    public ECloudUser getById(Serializable id) {
        return mapper.selectById(id);
    }

    /**
     * 根据ID来更新缓存信息
     *
     * @param id
     * @param entity
     * @return
     */
    @CacheEvict(cacheNames = CacheConstants.E_CLOUD_CACHE_NAME, keyGenerator = "userIdKeyGenerator")
    @Override
    public boolean updateById(Serializable id, ECloudUser entity) {
        return mapper.updateById(entity) == 1;
    }

    /**
     * 根据ID来删除缓存信息
     *
     * @param id
     * @return
     */
    @CacheEvict(cacheNames = CacheConstants.E_CLOUD_CACHE_NAME, keyGenerator = "userIdKeyGenerator")
    @Override
    public boolean removeById(Serializable id) {
        return mapper.deleteById(id) == 1;
    }

}
