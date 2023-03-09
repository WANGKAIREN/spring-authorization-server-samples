package com.ciglink.authcenter.authorizationserver.controller.test;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WANGKairen
 * @since 2023-01-22 23:45:58
 **/
@RestController
public class TestController {

    @GetMapping("/test")
    private String test() {
        return "test";
    }

    @GetMapping("/foo")
    public Map<String, Object> foo(@CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        Map<String, Object> map = new HashMap<>();
        String scopesAuthorized = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        map.put("authorized roles", scopesAuthorized);
        map.put("app", "this is a resource server uri");
        return map;
    }
}
