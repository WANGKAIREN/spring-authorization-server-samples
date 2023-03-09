package com.ciglink.authcenter.authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author WANGKairen
 * @since 2023-01-12 14:26:29
 **/
@SpringBootApplication
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
        System.out.println("授权服务启动成功");
    }
}
