package com.github.geemu.cloud.base.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * IO工具类
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 11:52
 */
public final class IOUtils {

    /**
     * 关闭
     * 关闭失败不会抛出异常
     * @param closeable 被关闭的对象
     */
    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }

    /**
     * 从一个大的（超过2GB）{@code InputStream}复制字节到{@code OutputStream}
     * @param input 输入流
     * @param output 输出流
     * @param buffer 用于复制的缓冲区
     * @return 复制的字节数
     * @throws IOException IOException
     */
    public static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer) throws IOException {
        long count = 0;
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * 字节从{@code InputStream}复制到{@code Writer}
     * @param input input
     * @param output output
     * @param inputEncoding 用于输入流的编码，空表示平台默认值
     * @throws NullPointerException NullPointerException
     * @throws IOException IOException
     */
    public static void copy(final InputStream input, final Writer output, final Charset inputEncoding) throws IOException {
        final InputStreamReader in = new InputStreamReader(input, inputEncoding);
        copy(in, output);
    }

    /**
     * 将字符从{@code Reader}复制到{@code Writer}
     * @param input input
     * @param output output
     * @return 复制的字符数
     * @throws NullPointerException NullPointerException
     * @throws IOException IOException
     */
    public static int copy(final Reader input, final Writer output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    /**
     * 将字符从一个大（超过2GB）的{@code Reader}复制到一个{@code Writer}
     * @param input input
     * @param output output
     * @return 复制的字符数
     * @throws NullPointerException NullPointerException
     * @throws IOException IOException
     */
    public static long copyLarge(final Reader input, final Writer output) throws IOException {
        return copyLarge(input, output, new char[1024 * 4]);
    }

    /**
     * 将字符从一个大（超过2GB）的{@code Reader}复制到一个{@code Writer}
     * @param input input
     * @param output output
     * @param buffer 用于复制的缓冲区
     * @return 复制的字符数
     * @throws NullPointerException NullPointerException
     * @throws IOException IOException
     */
    public static long copyLarge(final Reader input, final Writer output, final char[] buffer) throws IOException {
        long count = 0;
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * 文件转为流
     * @param file 文件
     * @return {@link FileInputStream}
     */
    public static FileInputStream toStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按行读取数据，针对每行的数据做处理<br>
     * {@link Reader}自带编码定义，因此读取数据的编码跟随其编码。
     * @param reader reader
     * @param lineHandler 行处理接口，实现handle方法用于编辑一行的数据后入到指定地方
     * @throws IOException IO异常
     */
    public static void readLines(Reader reader, LineHandler lineHandler) throws IOException {
        // 从返回的内容中读取所需内容
        final BufferedReader bReader = toBufferedReader(reader);
        String line;
        while (null != (line = bReader.readLine())) {
            lineHandler.handle(line);
        }
    }

    /**
     * 获取BufferedReader
     * @param reader reader
     * @return BufferedReader
     * @throws NullPointerException NullPointerException
     */
    public static BufferedReader toBufferedReader(final Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    /**
     * 逐行读取{@code InputStream}
     * @param input input
     * @param encoding encoding
     * @return 行列表
     * @throws NullPointerException NullPointerException
     * @throws IOException IOException
     */
    public static List<String> readLines(final InputStream input, final Charset encoding) throws IOException {
        final InputStreamReader reader = new InputStreamReader(input, encoding);
        return readLines(reader);
    }

    /**
     * 逐行读取{@code Reader}
     * @param input input
     * @return 行列表
     * @throws NullPointerException NullPointerException
     * @throws IOException IOException
     */
    public static List<String> readLines(final Reader input) throws IOException {
        final BufferedReader reader = toBufferedReader(input);
        final List<String> list = new ArrayList<>();
        String line = reader.readLine();
        while (null != line) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }

    /**
     * 输出到文件
     * @param file file
     * @param data data
     * @param encoding encoding
     * @param append 如果{@code true}，则字符串将添加到文件结束而不是覆盖
     * @throws IOException IOException
     */
    public static void writeStringToFile(final File file, final String data, final Charset encoding, final boolean append) throws IOException {
        try (OutputStream out = new FileOutputStream(file, append)) {
            write(data, out, encoding);
        }
    }

    /**
     * 输出
     * @param data data
     * @param output output
     * @param encoding encoding
     * @throws NullPointerException NullPointerException
     * @throws IOException IOException
     */
    public static void write(final String data, final OutputStream output, final Charset encoding) throws IOException {
        if (null != data) {
            output.write(data.getBytes(encoding));
        }
    }

}
