package com.ciglink.authcenter.test.client.credentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author WANGKairen
 * @since 2023-01-12 14:26:29
 **/
@EnableScheduling
@SpringBootApplication
public class TestClientCredentialsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestClientCredentialsApplication.class, args);
        System.out.println("客户端凭证模式-测试启动成功");
    }
}
