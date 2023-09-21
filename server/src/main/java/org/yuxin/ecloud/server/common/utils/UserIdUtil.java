package org.yuxin.ecloud.server.common.utils;

import org.yuxin.ecloud.core.constants.ECloudConstants;

import java.util.Objects;

/**
 * 用户ID存储工具类
 */
public class UserIdUtil {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程的用户ID
     *
     * @param userId
     */
    public static void set(Long userId) {
        threadLocal.set(userId);
    }

    /**
     * 获取当前线程的用户ID
     *
     * @return
     */
    public static Long get() {
        Long userId = threadLocal.get();
        if (Objects.isNull(userId)) {
            return ECloudConstants.ZERO_LONG;
        }
        return userId;
    }

}
