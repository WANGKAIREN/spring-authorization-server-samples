package com.example.authenticationjwt.config;

import com.example.authenticationjwt.security.RedirectLoginAuthenticationSuccessHandler;
import com.example.authenticationjwt.security.UnauthorizedAuthenticationEntryPoint;
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

	// @formatter:off
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
					// 放行 /login 为了 /login?client_id=messaging-client
					.regexMatchers("/login\\?client_id=.*")
					// 放行匹配
					.permitAll()

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
			//.formLogin(withDefaults())
			// 替换默认登录页面 .mvcMatchers("/login/**") 放行 /login?client_id=messaging-client，现在 .loginPage("/login") 永远不会触发
			//.loginPage("/login") // loginPage 有两个功能 完全匹配放行 和 异常处理重定向 /login
			// 指定处理登录请求 URL
			//.loginProcessingUrl("/oauth2/login/processing")
			// 放行 loginPage 及 loginProcessingUrl
			//.permitAll()
			//.and()

			// 登录成功处理
			//.successHandler(redirectLoginAuthenticationSuccessHandler)
			// 登录失败处理
			//.failureHandler(authenticationEntryPointFailureHandler)
			//.disable()
			//.and()

		    // 异常处理
			//.exceptionHandling(exceptions ->
			//	// 所有请求 /** 未认证，抛出异常，异常处理返回失败 Json
			//	//exceptions.authenticationEntryPoint(new UnauthorizedAuthenticationEntryPoint())
			//	// 认证端点 /oauth2/authorize 未认证，抛出异常，异常处理重定向 /login
			//	exceptions.authenticationEntryPoint(new ClientLoginUrlAuthenticationEntryPoint("/login"))
			//)
		;
		// @formatter:on
		return http.build();
	}
	// @formatter:on

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
