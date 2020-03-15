package com.github.geemu.cloud.app.manage.config;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


/**
 * Swagger配置，注有@Api的class生成文档，只在Dev环境生效.
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-10-25 20:29
 */
@Slf4j
@EnableSwagger2WebMvc
@Configuration
@Profile("dev")
public class SwaggerConfig {

    /**
     * 创建Swagger文档.
     * @return Docket Docket
     */
    @Bean
    public Docket createApi() {
        log.info("初始化:Swagger2");
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台管理")
                .apiInfo(new ApiInfoBuilder()
                        .title("Api文档")
                        .description("Api文档")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(ApiIgnore.class);
    }
}
