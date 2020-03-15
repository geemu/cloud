package com.github.geemu.cloud.base.ftp;

import com.github.geemu.cloud.base.CollectionUtils;
import com.github.geemu.cloud.base.StringUtils;
import com.github.geemu.cloud.base.io.FileUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 抽象FTP类，用于定义通用的FTP方法
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 11:17
 */
public abstract class AbstractFtp implements Closeable {

    /** 域名或IP **/
    protected String host;
    /** 端口 **/
    protected int port;
    /** 用户名 **/
    protected String user;
    /** 编码 **/
    protected String password;
    /** 密码 **/
    protected Charset charset;

    /**
     * 如果连接超时的话，重新进行连接
     * @return this
     */
    public abstract AbstractFtp reconnectIfTimeout();

    /**
     * 打开上级目录
     * @return 是否打开目录
     */
    public boolean toParent() {
        return cd("..");
    }

    /**
     * 打开指定目录
     * @param directory directory
     * @return 是否打开目录
     */
    public abstract boolean cd(String directory);

    /**
     * 文件或目录是否存在
     * @param path 目录
     * @return 是否存在
     */
    public boolean exist(String path) {
        final String fileName = FileUtils.getName(path);
        final String dir = StringUtils.removeSuffix(path, fileName);
        final List<String> names = ls(dir);
        return containsIgnoreCase(names, fileName);
    }

    /**
     * 遍历某个目录下所有文件和目录，不会递归遍历
     * @param path 需要遍历的目录
     * @return 文件和目录列表
     */
    public abstract List<String> ls(String path);

    /**
     * 是否包含指定字符串，忽略大小写
     * @param nameList 文件或目录名列表
     * @param nameToFind 要查找的文件或目录名
     * @return 是否包含
     */
    private static boolean containsIgnoreCase(List<String> nameList, String nameToFind) {
        if (CollectionUtils.isEmpty(nameList)) {
            return Boolean.FALSE;
        }
        if (StringUtils.isEmpty(nameToFind)) {
            return Boolean.FALSE;
        }
        for (String name : nameList) {
            if (nameToFind.equalsIgnoreCase(name)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 删除指定目录下的指定文件
     * @param path 目录路径
     * @return 是否存在
     */
    public abstract boolean delFile(String path);

    /**
     * 删除文件夹及其文件夹下的所有文件
     * @param dirPath 文件夹路径
     * @return boolean 是否删除成功
     */
    public abstract boolean delDir(String dirPath);

    /**
     * 创建指定文件夹及其父目录，从根目录开始创建，创建完成后回到默认的工作目录
     * @param dir 文件夹路径，绝对路径
     */
    public void mkDirs(String dir) {
        final String[] dirs = StringUtils.trim(dir).split("[\\\\/]+");
        final String now = pwd();
        if (dirs.length > 0 && StringUtils.isEmpty(dirs[0])) {
            // 首位为空，表示以/开头
            this.cd(StringUtils.SLASH);
        }
        for (String s : dirs) {
            if (StringUtils.isNotEmpty(s)) {
                if (!cd(s)) {
                    // 目录不存在时创建
                    mkdir(s);
                    cd(s);
                }
            }
        }
        // 切换回工作目录
        cd(now);
    }

    /**
     * 远程当前目录（工作目录）
     * @return 远程当前目录
     */
    public abstract String pwd();

    /**
     * 在当前远程目录（工作目录）下创建新的目录
     * @param dir 目录名
     * @return 是否创建成功
     */
    public abstract boolean mkdir(String dir);

    /**
     * 将本地文件上传到目标服务器，目标文件名为destPath，若destPath为目录，则目标文件名将与file文件名相同
     * 覆盖模式
     * @param destPath 服务端路径，可以为{@code null} 或者相对路径或绝对路径
     * @param file 需要上传的文件
     * @return 是否成功
     */
    public abstract boolean upload(String destPath, File file);

    /**
     * 下载文件
     * @param path 文件路径
     * @param outFile 输出文件或目录
     */
    public abstract void download(String path, File outFile) throws IOException;

}
