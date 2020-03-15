package com.github.geemu.cloud.app.gateway;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Gateway程序入口
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-11-07 22:33
 */
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.github.geemu.cloud.app.gateway"})
public class GatewayApp {

    /**
     * 主函数
     * @param args 运行参数
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GatewayApp.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
