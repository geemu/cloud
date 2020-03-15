package com.github.geemu.cloud.base;

/**
 * 数组工具类
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-06 22:24
 */
public final class ArrayUtils {

    /**
     * 数组是否为非空
     * @param <T> 数组元素类型
     * @param array 数组
     * @return 是否为非空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return null != array && 0 != array.length;
    }

}
