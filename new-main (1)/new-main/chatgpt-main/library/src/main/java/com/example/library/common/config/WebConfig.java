package com.example.library.common.config;

import com.example.library.common.security.SubsystemLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SubsystemLoginInterceptor())
                .addPathPatterns("/catalog/**", "/acquisition/**", "/periodical/**", "/statistics/**");
    }
}
