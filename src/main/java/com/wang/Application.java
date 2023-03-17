package com.wang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author jyw
 * @date 2018/8/9 15:42
 */
@SpringBootApplication
@MapperScan("com.wang.mapper")
//@EnableSwagger2
//@EnableWebSecurity
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
