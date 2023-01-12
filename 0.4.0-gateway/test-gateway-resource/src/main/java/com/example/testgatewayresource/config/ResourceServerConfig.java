package com.example.testgatewayresource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {

	/**
	 * 资源服务器需要与授权服务器通信校验令牌
	 * 1. /oauth/check_token：用于资源服务访问的令牌解析端点 生产不推荐，因为每次获取资源都要访问、校验太频繁
	 * 2. 授权服务器 保存到 数据库 或 Redis 资源服务器访问 Redis 校验令牌 推荐
	 *
	 * @param http http
	 * @return SecurityFilterChain
	 * @throws Exception Exception
	 */
	// @formatter:off
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.anyRequest()
			//.permitAll()
			.authenticated()
			.and()
			.oauth2ResourceServer()
			.jwt()

			//.mvcMatcher("/messages/**")
			//	.authorizeRequests()
			//		.mvcMatchers("/messages/**").access("hasAuthority('SCOPE_message.read')")
			//		.and()
			//.oauth2ResourceServer()
			//	.jwt()
			;
		return http.build();
	}
	// @formatter:on

}
