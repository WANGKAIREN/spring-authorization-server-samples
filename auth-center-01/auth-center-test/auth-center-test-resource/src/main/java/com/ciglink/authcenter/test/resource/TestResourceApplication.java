package com.ciglink.authcenter.test.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author WANGKairen
 * @since 2023-01-12 14:26:29
 **/
@SpringBootApplication
@ComponentScan(value = "com.ciglink") // 注入依赖包中的 Bean
public class TestResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestResourceApplication.class, args);
        System.out.println("资源服务-测试启动成功");
    }
}
