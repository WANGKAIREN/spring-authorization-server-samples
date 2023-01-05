package com.example.testgatewayresource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WANGKairen
 * @since 2023-01-05 11:43:42
 **/
@RestController
@RequestMapping("/resource/api")
public class ResourceApi {

    @GetMapping("/test1")
    public Map<String, Object> test1() {
        Map<String, Object> map = new HashMap<>();
        map.put("app", "this is a resource server uri test1");
        return map;
    }
}
