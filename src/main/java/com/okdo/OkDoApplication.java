package com.okdo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.okdo.mapper")
public class OkDoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OkDoApplication.class, args);
    }

}
