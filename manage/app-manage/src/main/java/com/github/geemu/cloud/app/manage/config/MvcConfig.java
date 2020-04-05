package com.github.geemu.cloud.app.manage.config;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * MVC配置
 * @author 陈方明  cfmmail@sina.com
 * @since 2019-06-10 21:30
 */
@Configuration
@AllArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    /**
     * 资源映射
     * @param registry 注册
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Swagger
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");

        registry.addResourceHandler("/swagger/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger*");

        registry.addResourceHandler("/v2/api-docs/**")
                .addResourceLocations("classpath:/META-INF/resources/v2/api-docs/");

        // 自定义资源
        registry.addResourceHandler("index.html")
                .addResourceLocations("classpath:/public/");

        registry.addResourceHandler("js/**")
                .addResourceLocations("classpath:/static/js/");

        registry.addResourceHandler("css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("img/**")
                .addResourceLocations("classpath:/static/img/");
    }

    /**
     * 自定义绑定
     * @param resolvers resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserMethodArgumentResolver);
    }

}
