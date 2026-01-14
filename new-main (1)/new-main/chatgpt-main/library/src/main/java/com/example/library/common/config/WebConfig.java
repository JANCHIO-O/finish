package com.example.library.common.config;

import com.example.library.common.security.SubsystemLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SubsystemLoginInterceptor("catalogAccountId", "/catalog/login"))
                .addPathPatterns("/catalog/**")
                .excludePathPatterns("/catalog/login", "/catalog/logout");
        registry.addInterceptor(new SubsystemLoginInterceptor("acquisitionAccountId", "/acquisition/login"))
                .addPathPatterns("/acquisition/**")
                .excludePathPatterns("/acquisition/login", "/acquisition/logout");
        registry.addInterceptor(new SubsystemLoginInterceptor("periodicalAccountId", "/periodical/login"))
                .addPathPatterns("/periodical/**")
                .excludePathPatterns("/periodical/login", "/periodical/logout");
        registry.addInterceptor(new SubsystemLoginInterceptor("statisticsAccountId", "/statistics/login"))
                .addPathPatterns("/statistics/**")
                .excludePathPatterns("/statistics/login", "/statistics/logout");
        registry.addInterceptor(new SubsystemLoginInterceptor("circulationAccountId", "/circulation/login"))
                .addPathPatterns("/circulation/**")
                .excludePathPatterns("/circulation/login", "/circulation/logout");
    }
}
