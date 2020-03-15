package com.github.geemu.cloud.app.manage.config;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;


/**
 * Swagger配置，注有@Api的class生成文档，只在Dev环境生效.
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-10-25 20:29
 */
@Slf4j
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    /**
     * 创建Swagger文档.
     * @return Docket Docket
     */
    @Bean
    public Docket createApi() {
        log.info("初始化:Swagger2");
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(400000).message("客户端请求参数缺失").responseModel(new ModelRef("BaseResponse")).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台管理")
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
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
