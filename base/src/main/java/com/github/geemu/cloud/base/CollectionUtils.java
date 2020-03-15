package com.github.geemu.cloud.base;

import java.util.Collection;

/**
 * 集合相关工具类
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-06 21:56
 */
public final class CollectionUtils {

    /**
     * 集合是否为空
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

}
