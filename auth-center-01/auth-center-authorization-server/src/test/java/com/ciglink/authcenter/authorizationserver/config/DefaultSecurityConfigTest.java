package com.ciglink.authcenter.authorizationserver.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.config.Customizer;

class DefaultSecurityConfigTest<T> {

    @Test
    public void test() {
        Customizer<Object> objectCustomizer = Customizer.withDefaults();
        System.out.println(Customizer.withDefaults());
    }

}