package com.example.customconsentauthorizationserver.config;

import com.example.customconsentauthorizationserver.security.RedirectLoginAuthenticationSuccessHandler;
import com.example.customconsentauthorizationserver.security.UnauthorizedAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;

/**
 * 认证 Authentication
 *
 * @author WANGKairen
 * @since 2023-01-06 10:34:08
 */
@EnableWebSecurity
public class DefaultSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		// 成功处理
		RedirectLoginAuthenticationSuccessHandler redirectLoginAuthenticationSuccessHandler = new RedirectLoginAuthenticationSuccessHandler();

		// 失败处理
		UnauthorizedAuthenticationEntryPoint unauthorizedAuthenticationEntryPoint = new UnauthorizedAuthenticationEntryPoint();
		AuthenticationEntryPointFailureHandler authenticationEntryPointFailureHandler = new AuthenticationEntryPointFailureHandler(unauthorizedAuthenticationEntryPoint);

		// @formatter:off
		http
			// 所有请求
			.authorizeRequests(authorizeRequests ->
					authorizeRequests
							// 所以请求
							.anyRequest()
							// 需要被认证
							.authenticated()
			)

			// 跨站请求攻击保护
			.csrf()
			.disable()

			// Form 表单认证方式 /login 会被 UsernamePasswordAuthenticationFilter 过滤器拦截
			.formLogin()

			// 登录成功处理
			//.successHandler(redirectLoginAuthenticationSuccessHandler)
			// 登录失败处理
			//.failureHandler(authenticationEntryPointFailureHandler)
			//.disable()
			//.and()

			// 异常处理
			//.exceptionHandling(exceptions ->
			//		// 所有请求 /** 未认证，抛出异常，异常处理返回失败 Json
			//		exceptions.authenticationEntryPoint(new UnauthorizedAuthenticationEntryPoint())
			//)
		;
		// @formatter:on
		return http.build();
	}

	// @formatter:off
	@Bean
	UserDetailsService users() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("user1")
				.password("password")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
	// @formatter:on

}
