package com.ciglink.authcenter.authorizationserver.config;

import com.ciglink.authcenter.authorizationserver.security.ClientLoginUrlAuthenticationEntryPoint;
import com.ciglink.authcenter.authorizationserver.security.oauth2.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OidcConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.time.Duration;
import java.util.UUID;

/**
 * 授权服务器
 *
 * @author WANGKairen
 * @since 2023-01-12 16:08:53
 **/
@Configuration(proxyBeanMethods = false) // Lite 模式
public class AuthorizationServerConfig {

    /**
     * 自定义同意端点
     */
    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

    /**
     * 授权服务安全过滤器链
     * <br>
     * 授权服务默认配置
     * {@link  OAuth2AuthorizationServerConfiguration#applyDefaultSecurity(HttpSecurity)}
     * OpenID Connect 1.0 端点
     * {@link OidcConfigurer}
     * 授权服务端点
     * {@link ProviderSettings#builder()}
     *
     * @param http
     * @return
     * @throws Exception
     */
    // @formatter:off
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) throws Exception {

        // 授权服务配置
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();

        // 自定义同意端点
        authorizationServerConfigurer
            .authorizationEndpoint(authorizationEndpoint ->
                authorizationEndpoint
                    .consentPage(CUSTOM_CONSENT_PAGE_URI)
            )
            // Enable OpenID Connect 1.0
            .oidc(Customizer.withDefaults())
        ;

        // 授权服务端点
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http
            // 对匹配的 HTTP 请求，基于安全考虑进行配置
            .requestMatcher(endpointsMatcher)
            // 授权请求
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    // 所以请求
                    .anyRequest()
                    // 需要被认证
                    .authenticated()
            )

            // 前后端分离登录
            // Spring Security 永远不会创建 HttpSession，也永远不会使用它来获取 SecurityContext
            //.sessionManagement()
            //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            //.and()
            /*
            解决授权端点认证时需要获取 SecurityContextHolder 问题
            SecurityContextPersistenceFilter 从 Session 中获取安全上下文信息 设置到 SecurityContextHolder
            将 token 验证过滤器 添加到 SecurityContextPersistenceFilter 之前会清空 SecurityContextHolder
            不添加则被 JwtAuthenticationProvider 拦截
             */
            .addFilterAfter(jwtAuthenticationTokenFilter, SecurityContextPersistenceFilter.class)

            // 跨站请求攻击保护
            .csrf(csrf ->
                csrf
                    // 忽略授权服务的端点
                    .ignoringRequestMatchers(endpointsMatcher)
            )
            // 异常处理 /login 可以只写路径，也可以直接写 https://...
            .exceptionHandling(exceptions ->
                // 认证端点 /oauth2/authorize 未认证，抛出异常，异常处理重定向 /login 控制器
                exceptions.authenticationEntryPoint(new ClientLoginUrlAuthenticationEntryPoint("/login"))
                //exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))

                // 认证端点 /oauth2/authorize 未认证，抛出异常，异常处理返回失败 Json
                //exceptions.authenticationEntryPoint(new UnauthorizedAuthenticationEntryPoint())
            )
            // access_token 令牌 为 JWT 令牌
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            // 应用配置
            .apply(authorizationServerConfigurer)
        ;
        // 构建安全过滤器链
        return http.build();
    }
    // @formatter:on

    // @formatter:off
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("messaging-client")
            .clientSecret("{noop}secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .redirectUri("http://127.0.0.1:30000/login/oauth2/code/messaging-client-oidc")
            .redirectUri("http://127.0.0.1:30000/authorized")
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .scope("message.read")
            .scope("message.write")
            .scope("auth-center-resource-test")
            .clientSettings(
                ClientSettings
                    .builder()
                    // 不需要用户同意
                    .requireAuthorizationConsent(false)
                    .build()
            )
            .tokenSettings(
                TokenSettings
                    .builder()
                        .accessTokenTimeToLive(Duration.ofDays(1))
                        .refreshTokenTimeToLive(Duration.ofDays(1))
                    .build()
            )
            .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }
    // @formatter:on

    /**
     * issuer-uri 不允许使用 127.0.0.1
     * 客户端、资源服务器的 issuer-uri 要与配置中的一致
     *
     * @return
     */
    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder().issuer("http://localhost:9001").build();
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService() {
        // Will be used by the ConsentController
        return new InMemoryOAuth2AuthorizationConsentService();
    }
}
