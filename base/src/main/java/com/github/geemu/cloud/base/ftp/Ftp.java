package com.github.geemu.cloud.base.ftp;


import com.github.geemu.cloud.base.ArrayUtils;
import com.github.geemu.cloud.base.StringUtils;
import com.github.geemu.cloud.base.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * FTP客户端封装
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 16:13
 */
public class Ftp extends AbstractFtp {

    /** 默认端口 */
    public static final int DEFAULT_PORT = 21;
    /** FTP客户端 */
    private FTPClient client;
    /** FTP连接模式 **/
    private FtpMode mode;
    /** 执行完操作是否返回当前目录 */
    private boolean backToPwd;

    /**
     * 构造，匿名登录
     * @param host 域名或IP
     */
    public Ftp(String host) {
        this(host, DEFAULT_PORT);
    }

    /**
     * 构造，匿名登录
     * @param host 域名或IP
     * @param port 端口
     */
    public Ftp(String host, int port) {
        this(host, port, "anonymous", "");
    }

    /**
     * 构造
     * @param host 域名或IP
     * @param port 端口
     * @param user 用户名
     * @param password 密码
     */
    public Ftp(String host, int port, String user, String password) {
        this(host, port, user, password, StandardCharsets.UTF_8);
    }

    /**
     * 构造
     * @param host 域名或IP
     * @param port 端口
     * @param user 用户名
     * @param password 密码
     * @param charset 编码
     */
    public Ftp(String host, int port, String user, String password, Charset charset) {
        this(host, port, user, password, charset, null);
    }

    /**
     * 构造
     * @param host 域名或IP
     * @param port 端口
     * @param user 用户名
     * @param password 密码
     * @param charset 编码
     * @param mode 模式
     */
    public Ftp(String host, int port, String user, String password, Charset charset, FtpMode mode) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.charset = charset;
        this.mode = mode;
        this.init();
    }

    /**
     * 初始化连接
     * @return this
     */
    public Ftp init() {
        return this.init(this.host, this.port, this.user, this.password, this.mode);
    }

    /**
     * 初始化连接
     * @param host 域名或IP
     * @param port 端口
     * @param user 用户名
     * @param password 密码
     * @param mode 模式
     * @return this
     */
    public Ftp init(String host, int port, String user, String password, FtpMode mode) {
        final FTPClient client = new FTPClient();
        client.setControlEncoding(this.charset.toString());
        try {
            // 连接ftp服务器
            client.connect(host, port);
            // 登录ftp服务器
            client.login(user, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final int replyCode = client.getReplyCode();
        // 是否成功登录服务器
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            try {
                client.disconnect();
            } catch (IOException e) {
                // ignore
            }
            throw new RuntimeException("Login failed for user [" + user + "], reply code is: [" + replyCode + "]");
        }
        this.client = client;
        if (null != mode) {
            setMode(mode);
        }
        return this;
    }

    /**
     * 设置FTP连接模式，可选主动和被动模式
     * @param mode 模式枚举
     * @return this
     */
    public Ftp setMode(FtpMode mode) {
        this.mode = mode;
        switch (mode) {
            case ACTIVE:
                this.client.enterLocalActiveMode();
                break;
            case PASSIVE:
                this.client.enterLocalPassiveMode();
                break;
        }
        return this;
    }

    /**
     * 初始化连接
     * @param host 域名或IP
     * @param port 端口
     * @param user 用户名
     * @param password 密码
     * @return this
     */
    public Ftp init(String host, int port, String user, String password) {
        return this.init(host, port, user, password, null);
    }

    /**
     * 设置执行完操作是否返回当前目录
     * @param backToPwd 执行完操作是否返回当前目录
     * @return this
     */
    public Ftp setBackToPwd(boolean backToPwd) {
        this.backToPwd = backToPwd;
        return this;
    }

    /**
     * 如果连接超时的话，重新进行连接
     * 经测试，当连接超时时，client.isConnected() {@code true}，无法判断是否连接超时 因此，通过发送pwd命令的方式，检查连接是否超时
     * @return this
     */
    @Override
    public Ftp reconnectIfTimeout() {
        String pwd = null;
        try {
            pwd = pwd();
        } catch (RuntimeException e) {
            // ignore
        }
        if (null == pwd) {
            return this.init();
        }
        return this;
    }

    /**
     * 改变目录
     * @param directory 目录
     * @return 是否成功
     */
    @Override
    public boolean cd(String directory) {
        if (StringUtils.isBlank(directory)) {
            return Boolean.FALSE;
        }
        try {
            return client.changeWorkingDirectory(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 遍历某个目录下所有文件和目录，不会递归遍历
     * @param path 需要遍历的目录
     * @return 文件和目录列表
     */
    @Override
    public List<String> ls(String path) {
        final FTPFile[] ftpFiles = lsFiles(path);
        final List<String> fileNames = new ArrayList<>();
        for (FTPFile ftpFile : ftpFiles) {
            fileNames.add(ftpFile.getName());
        }
        return fileNames;
    }

    /**
     * 遍历某个目录下所有文件和目录，不会递归遍历
     * @param path 目录
     * @return 文件或目录列表
     */
    public FTPFile[] lsFiles(String path) {
        String pwd = null;
        if (StringUtils.isNotBlank(path)) {
            pwd = pwd();
            cd(path);
        }

        FTPFile[] ftpFiles;
        try {
            ftpFiles = this.client.listFiles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 回到原目录
            cd(pwd);
        }
        return ftpFiles;
    }

    /**
     * 删除指定目录下的指定文件
     * @param path 目录路径
     * @return 是否存在
     */
    @Override
    public boolean delFile(String path) {
        final String pwd = pwd();
        final String fileName = FileUtils.getName(path);
        final String dir = StringUtils.removeSuffix(path, fileName);
        cd(dir);
        boolean success;
        try {
            success = client.deleteFile(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 回到原目录
            cd(pwd);
        }
        return success;
    }

    /**
     * 删除文件夹及其文件夹下的所有文件
     * @param dirPath 文件夹路径
     * @return boolean 是否删除成功
     */
    @Override
    public boolean delDir(String dirPath) {
        FTPFile[] dirs;
        try {
            dirs = client.listFiles(dirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String name;
        String childPath;
        for (FTPFile ftpFile : dirs) {
            name = ftpFile.getName();
            childPath = dirPath + "/" + name;
            if (ftpFile.isDirectory()) {
                // 上级和本级目录除外
                if (!".".equals(name) && !"..".equals(name)) {
                    delDir(childPath);
                }
            } else {
                delFile(childPath);
            }
        }
        // 删除空目录
        try {
            return this.client.removeDirectory(dirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 远程当前目录
     * @return 远程当前目录
     */
    @Override
    public String pwd() {
        try {
            return client.printWorkingDirectory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 在当前远程目录（工作目录）下创建新的目录
     * @param dir 目录名
     * @return 是否创建成功
     */
    @Override
    public boolean mkdir(String dir) {
        try {
            return this.client.makeDirectory(dir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将本地文件上传到目标服务器，目标文件名为destPath，若destPath为目录，则目标文件名将与file文件名相同
     * 覆盖模式
     * @param destPath 服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param file 需要上传的文件
     * @return 是否成功
     */
    @Override
    public boolean upload(String destPath, File file) {
        if (null == file) {
            throw new RuntimeException("上传文件不能为null");
        }
        return upload(destPath, file.getName(), file);
    }

    /**
     * 上传文件到指定目录
     * @param file 文件
     * @param path 服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param fileName 自定义在服务端保存的文件名
     * @return 是否上传成功
     */
    public boolean upload(String path, String fileName, File file) {
        try (InputStream in = FileUtils.getInputStream(file)) {
            return upload(path, fileName, in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传文件到指定目录
     * @param path 服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param fileName 文件名
     * @param fileStream 文件流
     * @return 是否上传成功
     */
    public boolean upload(String path, String fileName, InputStream fileStream) {
        try {
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String pwd = null;
        if (this.backToPwd) {
            pwd = pwd();
        }

        if (StringUtils.isNotBlank(path)) {
            mkDirs(path);
            boolean isOk = cd(path);
            if (!isOk) {
                return false;
            }
        }
        try {
            return client.storeFile(fileName, fileStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (this.backToPwd) {
                cd(pwd);
            }
        }
    }

    /**
     * 下载文件
     * @param path 文件路径
     * @param outFile 输出文件或目录
     * @throws IOException IOException
     */
    @Override
    public void download(String path, File outFile) throws IOException {
        final String fileName = FileUtils.getName(path);
        final String dir = StringUtils.removeSuffix(path, fileName);
        download(dir, fileName, outFile);
    }

    /**
     * 下载文件
     * @param path 文件路径
     * @param fileName 文件名
     * @param outFile 输出文件或目录
     * @throws IOException IOException
     */
    public void download(String path, String fileName, File outFile) throws IOException {
        if (outFile.isDirectory()) {
            outFile = new File(outFile, fileName);
        }
        if (!outFile.exists()) {
            FileUtils.touch(outFile);
        }
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(outFile))) {
            download(path, fileName, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 下载文件到输出流
     * @param path 文件路径
     * @param fileName 文件名
     * @param out 输出位置
     */
    public void download(String path, String fileName, OutputStream out) {
        String pwd = null;
        if (this.backToPwd) {
            pwd = pwd();
        }
        cd(path);
        try {
            client.setFileType(FTPClient.BINARY_FILE_TYPE);
            client.retrieveFile(fileName, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (backToPwd) {
                cd(pwd);
            }
        }
    }

    /**
     * 判断ftp服务器文件是否存在
     * @param path 文件路径
     * @return 是否存在
     */
    public boolean existFile(String path) {
        FTPFile[] ftpFileArr;
        try {
            ftpFileArr = client.listFiles(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ArrayUtils.isNotEmpty(ftpFileArr);
    }

    /**
     * 获取FTPClient客户端对象
     * @return {@link FTPClient}
     */
    public FTPClient getClient() {
        return this.client;
    }

    @Override
    public void close() throws IOException {
        if (null != this.client) {
            this.client.logout();
            if (this.client.isConnected()) {
                this.client.disconnect();
            }
            this.client = null;
        }
    }

}
