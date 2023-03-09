package com.ciglink.authcenter.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author WANGKairen
 * @since 2023-02-01 17:28:20
 **/
@Configuration(proxyBeanMethods = false)
public class UserDetailsConfig {

    // @formatter:off
    @Bean
    UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("password")
                .roles("USER","admin")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
    // @formatter:on

}
