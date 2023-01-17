package com.example.customconsentauthorizationserver.config;

import com.example.customconsentauthorizationserver.jose.Jwks;
import com.example.customconsentauthorizationserver.security.UnauthorizedAuthenticationEntryPoint;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
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
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.UUID;

/**
 * 授权 Authorization
 *
 * @author WANGKairen
 * @since 2023-01-06 10:34:08
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {

	/**
	 * 授权服务安全过滤器链
	 * <br>
	 * 可参考，授权服务默认配置
	 * {@link  OAuth2AuthorizationServerConfiguration#applyDefaultSecurity(HttpSecurity)}
	 * 端点查看
	 * {@link AuthorizationServerSettings#builder()}
	 *
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

		// 授权服务配置
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
		// Enable OpenID Connect 1.0
		authorizationServerConfigurer.oidc(Customizer.withDefaults());
		// 授权服务端点
		RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

		// @formatter:off
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
			// 跨站请求攻击保护
			.csrf(csrf ->
				csrf
					// 忽略授权服务的端点
					.ignoringRequestMatchers(endpointsMatcher)
			)
			// 异常处理
			.exceptionHandling(exceptions ->
				// 认证端点 /oauth2/authorize 未认证，抛出异常，异常处理重定向 /login
				exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
				// 认证端点 /oauth2/authorize 未认证，抛出异常，异常处理返回失败 Json
				//exceptions.authenticationEntryPoint(new UnauthorizedAuthenticationEntryPoint())
			)
			// JWT 令牌
			.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
			// 应用配置
			.apply(authorizationServerConfigurer)
		;
		// @formatter:on
		// 构建安全过滤器链
		return http.build();
	}

	// @formatter:off
	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
			.clientId("messaging-client")
			.clientSecret("{noop}secret")
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
			// 授权码模式
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			// 刷新令牌
			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
			// 客户端凭证模式
			.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
			.redirectUri("http://127.0.0.1:30000/login/oauth2/code/messaging-client-oidc")
			.redirectUri("http://127.0.0.1:30000/authorized")
			.scope(OidcScopes.OPENID)
			.scope(OidcScopes.PROFILE)
			.scope("message.read")
			.scope("message.write")
			.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
			.build();
		return new InMemoryRegisteredClientRepository(registeredClient);
	}
	// @formatter:on

	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		RSAKey rsaKey = Jwks.generateRsa();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

	@Bean
	public OAuth2AuthorizationConsentService authorizationConsentService() {
		// Will be used by the ConsentController
		return new InMemoryOAuth2AuthorizationConsentService();
	}

}
