package com.github.geemu.cloud.app.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后台管理程序入口
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-11-16 15:10
 */
//@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.github.geemu.cloud.app.manage"})
@MapperScan("com.github.geemu.cloud.mapper.manage")
public class ManageApp {

    /**
     * 主函数
     * @param args 运行参数
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ManageApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
