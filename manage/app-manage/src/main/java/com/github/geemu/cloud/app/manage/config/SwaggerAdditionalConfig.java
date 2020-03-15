package com.github.geemu.cloud.app.manage.config;

import com.fasterxml.classmate.TypeResolver;
import com.github.geemu.cloud.base.entity.BaseResponseState;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Swagger额外接口
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-03-15 18:36
 */
@Component
public class SwaggerAdditionalConfig implements ApiListingScannerPlugin {

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

}
