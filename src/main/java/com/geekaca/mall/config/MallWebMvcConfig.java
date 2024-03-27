package com.geekaca.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MallWebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + "c:\\dev\\codes\\newbee-mall-api\\static-files\\goods-img\\");
        registry.addResourceHandler("/goods-img/**").addResourceLocations("file:" + "c:\\dev\\codes\\newbee-mall-api" +
                "\\static-files\\goods-img\\");
    }

    // 设置允许跨域访问
    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        //任意请求路径        允许来自任何域名的访问
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true).maxAge(3600);
    }
}
