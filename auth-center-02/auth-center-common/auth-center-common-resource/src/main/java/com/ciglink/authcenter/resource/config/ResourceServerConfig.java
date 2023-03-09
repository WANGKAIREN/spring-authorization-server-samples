package com.ciglink.authcenter.resource.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author WANGKairen
 * @since 2023-02-01 10:38:43
 */
@EnableWebSecurity
public class ResourceServerConfig {

	@Value("${spring.application.name}")
	private String appName;

	/**
	 * scope 是客户端的
	 * role 是用户的
	 * <br>
	 * 核心组件
	 * {@link BearerTokenAuthenticationFilter}
	 * {@link JwtDecoder}
	 * {@link OAuth2ResourceServerProperties}
	 * {@link JwtAuthenticationConverter} 可以覆盖此类，更加灵活转换 JWT 中 权限 信息 TODO 计划期望转换多个 Principal 中 claims 权限 key，需要授权服务器添加权限 key，或资源服务器获取 /userinfo 端点
	 *
	 * @param http
	 * @return
	 * @throws Exception
	 */
	// @formatter:off
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("资源服务器初始化：" + appName);

		http
			// 只拦截匹配 URL
			//.mvcMatcher("/messages/**")

			.authorizeRequests(authorizeRequests ->
				authorizeRequests
					// 对 mvcMatcher 匹配后的下一级 URL 进行权限细化
					.mvcMatchers("/messages/**")
					//.access("hasAuthority('SCOPE_" + appName + "')")
					// 两种写法校验 scope
					//.access("hasAuthority('SCOPE_message.read')")
					//.hasAnyAuthority("SCOPE_message.read")

					// 资源服务器 Token 解析拿到 scope，没有 role 相关，授权服务器的用户访问有 role，没有 scope
					/*
					 Token 中没有 role，难道要让我自己加？那是否合适？
					 FilterSecurityInterceptor 类校验权限
					 AbstractSecurityInterceptor#beforeInvocation() 中查看 Authentication 的 authorities
					 */
					//.hasRole("USER")
					.hasRole("admin")

					.anyRequest()
					.authenticated()
			)

			// 配置 OAuth 2.0 资源服务器支持
			.oauth2ResourceServer()
			.jwt();

		return http.build();
	}
	// @formatter:on

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		// 负责转换 权限 信息
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		// 指定获取 Principal 中 claims 权限 key 为 authorities
		//grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
		// 默认 Principal 中 claims 权限 key 为 scope 会被解析为 Authenticated 的 authorities
		//grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
		// 去除权限 value 前缀 SCOPE_
		//grantedAuthoritiesConverter.setAuthorityPrefix("");

		// 实例负责从 JWT 中转换 认证 信息
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

}
