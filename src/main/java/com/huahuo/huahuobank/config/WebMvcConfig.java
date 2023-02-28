package com.huahuo.huahuobank.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.huahuo.huahuobank") //全局异常处理类需要被扫描才能
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/api/1/**")
                .excludePathPatterns("/io")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/upload/**")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/api/1/save/open/id")
        ;

    }

}