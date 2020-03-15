package com.github.geemu.cloud.base.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 11:50
 */
public final class FileUtils {

    /**
     * 返回文件名
     * @param filePath 文件
     * @return 文件名
     */
    public static String getName(String filePath) {
        if (null == filePath) {
            return null;
        }
        int len = filePath.length();
        if (0 == len) {
            return filePath;
        }
        char charAt = filePath.charAt(len - 1);
        if ('/' == charAt || '\\' == charAt) {
            // 以分隔符结尾的去掉结尾分隔符
            len--;
        }

        int begin = 0;
        char c;
        for (int i = len - 1; i > -1; i--) {
            c = filePath.charAt(i);
            if ('/' == c || '\\' == c) {
                // 查找最后一个路径分隔符（/或者\）
                begin = i + 1;
                break;
            }
        }
        return filePath.substring(begin, len);
    }

    /**
     * 获得输入流
     * @param file 文件
     * @return 输入流
     * @throws RuntimeException 异常
     */
    public static BufferedInputStream getInputStream(File file) {
        return new BufferedInputStream(IOUtils.toStream(file));
    }

    /**
     * 创建文件及其父目录，如果这个文件存在，直接返回这个文件
     * @param file file
     * @throws IOException IOException
     */
    public static void touch(final File file) throws IOException {
        if (!file.exists()) {
            new FileOutputStream(file, Boolean.FALSE).close();
        }
        final boolean success = file.setLastModified(System.currentTimeMillis());
        if (!success) {
            throw new IOException("Unable to set the last modification time for " + file);
        }
    }

    /**
     * 创建父目录
     * @param file file
     * @throws NullPointerException NullPointerException
     * @throws IOException IOException
     */
    public static void forceMkdirParent(final File file) throws IOException {
        final File parent = file.getParentFile();
        if (null == parent) {
            return;
        }
        forceMkdir(parent);
    }

    /**
     * 创建目录
     * @param directory directory
     * @throws NullPointerException NullPointerException
     * @throws IOException IOException
     */
    public static void forceMkdir(final File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                final String message = "File " + directory + " exists and is " + "not a directory. Unable to create directory.";
                throw new IOException(message);
            }
        } else {
            if (!directory.mkdirs()) {
                // 再次检查其他线程或进程是否未在后台创建目录
                if (!directory.isDirectory()) {
                    final String message = "Unable to create directory " + directory;
                    throw new IOException(message);
                }
            }
        }
    }

    /**
     * 从文件中读取每一行数据
     * @param file 文件
     * @param charset 编码
     * @return 文件中的每行内容的集合
     * @throws RuntimeException IO异常
     */
    public List<String> readLines(File file, Charset charset) throws IOException {
        List<String> list = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line = bufferedReader.readLine();
            while (null != line) {
                list.add(line);
                line = bufferedReader.readLine();
            }
            return list;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

}
