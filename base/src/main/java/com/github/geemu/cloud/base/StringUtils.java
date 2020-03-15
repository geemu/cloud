package com.github.geemu.cloud.base;

/**
 * 字符串工具类工具类
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 16:23
 */
public final class StringUtils {

    /** 左斜杠 **/
    public static final String SLASH = "/";

    /**
     * 字符串是否为非空白
     * @param cs 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 字符串是否为空
     * @param cs 被检测的字符串
     * @return 是否为空
     */
    public static boolean isEmpty(CharSequence cs) {
        return null == cs || 0 == cs.length();
    }

    /**
     * 字符串是否为非空白
     * @param cs 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 字符串是否为空白
     * @param cs 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(CharSequence cs) {
        int length;
        if ((null == cs) || 0 == ((length = cs.length()))) {
            return Boolean.TRUE;
        }
        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (!CharUtils.isBlankChar(cs.charAt(i))) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 除去字符串头尾部的空白，如果字符串是{@code null}，依然返回{@code null}
     * @param cs 要处理的字符串
     * @return 除去头尾空白的字符串，如果原字串为{@code null}，则返回{@code null}
     */
    public static String trim(CharSequence cs) {
        return (null == cs) ? null : trim(cs, 0);
    }

    /**
     * 除去字符串头尾部的空白符，如果字符串是{@code null}，依然返回{@code null}
     * @param cs 要处理的字符串
     * @param mode {@code -1}表示trimStart，{@code 0}表示trim全部， {@code 1}表示trimEnd
     * @return 除去指定字符后的的字符串，如果原字串为{@code null}，则返回{@code null}
     */
    public static String trim(CharSequence cs, int mode) {
        if (null == cs) {
            return null;
        }
        int length = cs.length();
        int start = 0;
        int end = length;
        // 扫描字符串头部
        if (mode <= 0) {
            while ((start < end) && (CharUtils.isBlankChar(cs.charAt(start)))) {
                start++;
            }
        }
        // 扫描字符串尾部
        if (mode >= 0) {
            while ((start < end) && (CharUtils.isBlankChar(cs.charAt(end - 1)))) {
                end--;
            }
        }
        if ((start > 0) || (end < length)) {
            return cs.toString().substring(start, end);
        }
        return cs.toString();
    }

    /**
     * 去掉指定后缀
     * @param cs 字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix,返回原字符串
     */
    public static String removeSuffix(CharSequence cs, CharSequence suffix) {
        if (isEmpty(cs) || isEmpty(suffix)) {
            return null == cs ? null : cs.toString();
        }
        final String text = cs.toString();
        if (text.endsWith(suffix.toString())) {
            // 截取前半段
            return cs.subSequence(0, text.length() - suffix.length()).toString();
        }
        return text;
    }

}
