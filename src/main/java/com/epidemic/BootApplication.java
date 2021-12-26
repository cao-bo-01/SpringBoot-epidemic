package com.epidemic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.epidemic.mapper")
@SpringBootApplication
@EnableScheduling
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
        System.out.println("==============曹老板跑起来==============");
    }
}
