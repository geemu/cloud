package com.github.geemu.cloud.base.ftp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * FTP连接模式
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 16:13
 */
@Getter
@ToString
@AllArgsConstructor
public enum FtpMode {

    /** 主动模式 **/
    ACTIVE,
    /** 被动模式 **/
    PASSIVE

}
