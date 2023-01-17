package com.example.testgateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;

/**
 * @author WANGKairen
 * @since 2023-01-05 13:41:52
 **/
@EnableWebFluxSecurity
public class OAuth2LoginSecurityConfig {

    // @formatter:off
    @Bean
    @ConditionalOnMissingBean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange()
            .anyExchange()
            .authenticated()
            .and()

            .oauth2Login()
            .and()

            .logout()
            .and()

            //.oauth2Client()
            //.and()

            .csrf()
            .disable()
        ;

        DelegatingServerLogoutHandler logoutHandler = new DelegatingServerLogoutHandler(
                new WebSessionServerLogoutHandler(), new SecurityContextServerLogoutHandler()
        );

        http.logout((logout) -> logout.logoutHandler(logoutHandler));

        return http.build();
    }
    // @formatter:on

}
