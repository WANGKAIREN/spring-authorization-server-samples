package com.ciglink.authorizationserver;

import com.ciglink.common.security.annotation.EnableCustomConfig;
import com.ciglink.common.security.annotation.EnableRyFeignClients;
import com.ciglink.common.swagger.annotation.EnableCustomSwagger2;
import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@EnableMPP// 启动mybatis-plus-plus
public class AuthorizationserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationserverApplication.class, args);
        System.out.println("认证模块启动成功");
    }

}
