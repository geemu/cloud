package com.github.geemu.cloud.base;

/**
 * 字符工具类
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 16:13
 */
public final class CharUtils {

    /**
     * 是否空白符
     * 空白符包括空格、制表符、全角空格和不间断空格
     * @param c 字符
     * @return 是否空白符
     */
    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    /**
     * 是否空白符
     * 空白符包括空格、制表符、全角空格和不间断空格
     * @param c 字符
     * @return 是否空白符
     */
    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c) || '\ufeff' == c || '\u202a' == c;
    }

}
