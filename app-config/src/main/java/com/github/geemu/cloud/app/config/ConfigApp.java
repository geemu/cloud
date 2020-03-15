package com.github.geemu.cloud.app.config;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Config配置中心程序入口
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 12:44
 */
@EnableEurekaClient
@EnableConfigServer
@SpringBootApplication(scanBasePackages = {"com.github.geemu.cloud.app.config"})
public class ConfigApp {

    /**
     * 主函数
     * @param args 运行参数
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConfigApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
