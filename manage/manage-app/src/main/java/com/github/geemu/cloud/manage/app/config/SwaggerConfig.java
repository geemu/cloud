package com.github.geemu.cloud.manage.app.config;

import com.fasterxml.classmate.TypeResolver;
import com.github.geemu.cloud.base.entity.BaseResponseState;
import com.github.geemu.cloud.manage.app.config.security.UserDetail;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Operation;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


/**
 * Swagger配置，注有@Api的class生成文档
 * @author 陈方明  cfmmail@sina.com
 * @since 2018-10-25 20:29
 */
@Slf4j
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig implements ApiListingScannerPlugin {

    private static final ResponseMessage RESPONSE_MESSAGE = new ResponseMessageBuilder()
            .code(BaseResponseState.SUCCESS_200000.getCode())
            .message(BaseResponseState.SUCCESS_200000.getMessage())
            .build();

    @Override
    public List<ApiDescription> apply(DocumentationContext context) {
        Operation loginMethod = new OperationBuilder(new CachingOperationNameGenerator())
                .method(HttpMethod.POST)
                .produces(new HashSet<String>() {{add(MediaType.APPLICATION_FORM_URLENCODED_VALUE);}})
                .summary("登录")
                .notes("登录")
                .tags(new HashSet<String>() {{add("登录");}})
                .parameters(Arrays.asList(
                        new ParameterBuilder()
                                .type(new TypeResolver().resolve(String.class))
                                .description("用户名")
                                .name("username")
                                .parameterType("form")
                                .required(Boolean.TRUE)
                                .modelRef(new ModelRef("String"))
                                .build(),
                        new ParameterBuilder()
                                .type(new TypeResolver().resolve(String.class))
                                .description("密码")
                                .name("password")
                                .parameterType("form")
                                .required(Boolean.TRUE)
                                .modelRef(new ModelRef("String"))
                                .build()

                ))
                .position(0)
                .responseModel(new ModelRef("BaseResponse"))
                .responseMessages(new HashSet<ResponseMessage>() {{add(RESPONSE_MESSAGE);}})
                .build();
        ApiDescription apiDescription = new ApiDescription("后台管理", "/login", "登录", Collections.singletonList(loginMethod), Boolean.FALSE);
        return Collections.singletonList(apiDescription);
    }

    /**
     * 是否支持
     * @param documentationType 文档类型
     * @return {@code true}支持、{@code false}不支持
     */
    @Override
    public boolean supports(DocumentationType documentationType) {
        return DocumentationType.SWAGGER_2.equals(documentationType);
    }

    /**
     * 创建Swagger文档
     * @return Docket Docket
     */
    @Bean
    public Docket createApi() {
        log.info("初始化:Docket");
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(400000).message("客户端请求参数缺失").responseModel(new ModelRef("BaseResponse")).build());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台管理")
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .securitySchemes(Collections.singletonList(new ApiKey("x-auth-token", "x-auth-token", "header")))
                .securityContexts(securityContexts())
                .apiInfo(new ApiInfoBuilder()
                        .title("Api文档")
                        .description("Api文档")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(ApiIgnore.class)
                .ignoredParameterTypes(UserDetail.class)
                ;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!login).*$"))
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

}
