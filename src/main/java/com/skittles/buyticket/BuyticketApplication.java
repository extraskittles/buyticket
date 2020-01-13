package com.skittles.buyticket;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan(value = "com.skittles.buyticket.mapper")
@MapperScan({"com.skittles.buyticket.mapper","com.skittles.buyticket.detailMapper"})
public class BuyticketApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuyticketApplication.class, args);
    }

}
