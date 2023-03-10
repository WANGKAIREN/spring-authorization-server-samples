package com.ciglink.authorizationserver.config;

import com.ciglink.authorizationserver.service.OAuth2ClientRegisteredService;
import com.ciglink.authorizationserver.service.OAuth2ClientService;
import com.ciglink.authorizationserver.service.RootUserDetailsService;
import com.ciglink.authorizationserver.service.RootUserInfoService;
import com.ciglink.authorizationserver.service.impl.JdbcOAuth2ClientRegisteredServiceImpl;
import com.ciglink.authorizationserver.service.impl.JdbcRootUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.UUID;

/**
 * ?????????????????????
 * ??????????????????????????????????????????
 *
 * @author WANGKairen
 * @since 2022-12-27 10:06:49
 **/
//@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

    /**
     * Authorization server ?????? ?????????????????????
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean("authorizationServerSecurityFilterChain")
    @Order(Ordered.HIGHEST_PRECEDENCE) // ???????????????????????????
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

        // OAuth2 ????????????????????????????????????????????????????????????????????????
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        // Enable OpenID Connect 1.0????????????????????????????????????????????????????????????????????????
        authorizationServerConfigurer.oidc(Customizer.withDefaults());

        //authorizationServerConfigurer.authorizationEndpoint(authorizationEndpointConfigurer ->
        //    authorizationEndpointConfigurer.consentPage(CUSTOM_CONSENT_PAGE_URI)
        //);

        // ??????????????????????????????????????????
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        // @formatter:off
        http
            // ??????????????????????????????????????????
            .requestMatcher(endpointsMatcher)
            .authorizeRequests(authorizeRequests ->
                authorizeRequests.anyRequest().authenticated()
            )
            // ???????????????????????????????????????????????? csrf
            .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
            // formLogin ??? LoginUrlAuthenticationEntryPoint ?????????????????????
            .formLogin()
            // ????????????????????????
            //.loginPage("/login").permitAll()
            // ???????????????????????? URL
            //.loginProcessingUrl("/oauth2/login.do").permitAll()
            .and()
            //.exceptionHandling(exceptions ->
            //    exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            //)
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            // ??????????????????????????????
            .apply(authorizationServerConfigurer)
            //.and()
            //.oauth2ResourceServer().jwt()
        ;
        // @formatter:on

        return http.build();
    }

    /**
     * ????????????OAuth2?????????
     *
     * @return the oauth2 client registered service
     */
    //@Bean
    //@ConditionalOnMissingBean
    OAuth2ClientRegisteredService oAuth2ClientRegisteredService(OAuth2ClientService oAuth2ClientService) {
        return new JdbcOAuth2ClientRegisteredServiceImpl(oAuth2ClientService);
    }

    /**
     * ?????????OAuth2?????????
     * @return
     */
    // @formatter:off
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
                .redirectUri("http://127.0.0.1:8080/authorized")
                .redirectUri("http://127.0.0.1:30000/login/oauth2/code/messaging-client")
                .redirectUri("http://127.0.0.1:28181/login/oauth2/code/gatewayclient-wkr")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("message.read")
                .scope("message.write")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }
    // @formatter:on

    /**
     * ??????????????????????????????
     *
     * @param port the port
     * @return the authorization server settings
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings(@Value("${server.port}") Integer port) {
        //TODO ???????????????????????? ??????????????????issuer-uri
        return AuthorizationServerSettings.builder().issuer("http://localhost:" + port).build();
    }
}
