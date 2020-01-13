package com.skittles.buyticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.skittles.buyticket.controller"})
public class swaggerConfig {
    @Bean
    public Docket customDocket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiinfo());
    }

    private ApiInfo apiinfo(){
        Contact contact=new Contact("skittles","www.test.com","583846780@qq.com");
        return new ApiInfoBuilder()
                .title("skittles项目API接口")
                .description("包括用户、商家、购物车、订单的接口")
                .contact(contact)
                .version("1.1.0")
                .build();
    }
}
