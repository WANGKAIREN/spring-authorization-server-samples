package com.example.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author WANGKairen
 * @since 2022-12-19 16:28:33
 **/
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test-" + LocalDateTime.now();
    }
}
