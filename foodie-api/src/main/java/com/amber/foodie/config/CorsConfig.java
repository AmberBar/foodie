package com.amber.foodie.config;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@NoArgsConstructor
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 添加cors的配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许哪些域名
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        // 允许使用cookie和session
        corsConfiguration.setAllowCredentials(true);
        // 允许的请求方式
        corsConfiguration.addAllowedMethod("*");
        // 允许请求头信息
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
