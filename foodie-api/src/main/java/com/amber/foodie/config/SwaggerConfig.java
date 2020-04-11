package com.amber.foodie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // 配置Swagger2 的核心配置Swagger
    @Bean
    public Docket createDocket() {
        return new Docket(DocumentationType.SWAGGER_2) // 文档的类型
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.amber.foodie.controller")) //指定controller
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Amber电商平台Api")
                .contact(new Contact("amber", "url", "amber@163.com"))
                .description("电商平台Api文档")
                .version("0.0.1")
                .termsOfServiceUrl("网站地址")
                .build();
    }

}
