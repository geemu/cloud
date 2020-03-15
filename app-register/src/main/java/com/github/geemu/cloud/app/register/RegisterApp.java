package com.github.geemu.cloud.app.register;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心程序入口
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 12:16
 */
@EnableEurekaServer
@SpringBootApplication(scanBasePackages = {"com.github.geemu.cloud.app.register"})
public class RegisterApp {

    /**
     * 主函数
     * @param args 运行参数
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RegisterApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
