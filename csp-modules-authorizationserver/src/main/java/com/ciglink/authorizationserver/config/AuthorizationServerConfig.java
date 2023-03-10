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
 * 授权服务器配置
 * 拦截授权服务器相关的请求端点
 *
 * @author WANGKairen
 * @since 2022-12-27 10:06:49
 **/
//@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

    /**
     * Authorization server 集成 优先级要高一些
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean("authorizationServerSecurityFilterChain")
    @Order(Ordered.HIGHEST_PRECEDENCE) // 最低值，最高优先级
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

        // OAuth2 授权服务器配置器，可以根据需求进行一些个性化配置
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        // Enable OpenID Connect 1.0，之后需要看如何关闭，现在关闭，网关模块启动失败
        authorizationServerConfigurer.oidc(Customizer.withDefaults());

        //authorizationServerConfigurer.authorizationEndpoint(authorizationEndpointConfigurer ->
        //    authorizationEndpointConfigurer.consentPage(CUSTOM_CONSENT_PAGE_URI)
        //);

        // 返回授权服务器相关的请求端点
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        // @formatter:off
        http
            // 拦截授权服务器相关的请求端点
            .requestMatcher(endpointsMatcher)
            .authorizeRequests(authorizeRequests ->
                authorizeRequests.anyRequest().authenticated()
            )
            // 忽略掉授权服务器相关的请求端点的 csrf
            .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
            // formLogin 和 LoginUrlAuthenticationEntryPoint 功能可能一样？
            .formLogin()
            // 替换默认登录页面
            //.loginPage("/login").permitAll()
            // 指定处理登录请求 URL
            //.loginProcessingUrl("/oauth2/login.do").permitAll()
            .and()
            //.exceptionHandling(exceptions ->
            //    exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            //)
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            // 应用授权服务器的配置
            .apply(authorizationServerConfigurer)
            //.and()
            //.oauth2ResourceServer().jwt()
        ;
        // @formatter:on

        return http.build();
    }

    /**
     * 数据库，OAuth2客户端
     *
     * @return the oauth2 client registered service
     */
    //@Bean
    //@ConditionalOnMissingBean
    OAuth2ClientRegisteredService oAuth2ClientRegisteredService(OAuth2ClientService oAuth2ClientService) {
        return new JdbcOAuth2ClientRegisteredServiceImpl(oAuth2ClientService);
    }

    /**
     * 内存，OAuth2客户端
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
     * 配置授权服务器元信息
     *
     * @param port the port
     * @return the authorization server settings
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings(@Value("${server.port}") Integer port) {
        //TODO 生产应该使用域名 客户端配置：issuer-uri
        return AuthorizationServerSettings.builder().issuer("http://localhost:" + port).build();
    }
}
