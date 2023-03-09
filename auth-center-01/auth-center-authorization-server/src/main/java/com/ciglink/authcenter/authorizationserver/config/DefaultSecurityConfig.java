package com.ciglink.authcenter.authorizationserver.config;

import com.ciglink.authcenter.authorizationserver.security.OAuth2RedirectLoginAuthenticationSuccessHandler;
import com.ciglink.authcenter.authorizationserver.security.OAuth2UnauthorizedAuthenticationEntryPoint;
import com.ciglink.authcenter.authorizationserver.security.oauth2.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;

/**
 * OAuth2 认证 Authentication
 *
 * @author WANGKairen
 * @since 2023-01-06 10:34:08
 */
@EnableWebSecurity
public class DefaultSecurityConfig {

	// @formatter:off
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE + 1)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) throws Exception {
		// 成功处理
		OAuth2RedirectLoginAuthenticationSuccessHandler OAuth2RedirectLoginAuthenticationSuccessHandler = new OAuth2RedirectLoginAuthenticationSuccessHandler();

		// 失败处理
		OAuth2UnauthorizedAuthenticationEntryPoint OAuth2UnauthorizedAuthenticationEntryPoint = new OAuth2UnauthorizedAuthenticationEntryPoint();
		AuthenticationEntryPointFailureHandler authenticationEntryPointFailureHandler = new AuthenticationEntryPointFailureHandler(OAuth2UnauthorizedAuthenticationEntryPoint);

		// @formatter:off
		http
			// 所有请求
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
					// 放行 /login 为了 /login?client_id=messaging-client
					.regexMatchers("/login\\?client_id=.*")
					// 允许匿名访问，登录后不能访问
					.anonymous()
					// 匿名访问，登录请求
					.mvcMatchers("/oauth2/login/json").anonymous()
					// 系统资源 TODO 暂时为测资源服务器的 hasRole 不生效问题
					.mvcMatchers("/test").hasRole("USER")
					// 所以请求
					.anyRequest()
					// 需要被认证
					.authenticated()
			)

			// 跨站请求攻击保护
			.csrf()
			.disable()

			// 表单登录
			// Form 表单认证方式 /login 会被 UsernamePasswordAuthenticationFilter 过滤器拦截，开启表单后，只会多此一个过滤器
			.formLogin()
			//.disable()
			//.formLogin(withDefaults())
			// 替换默认登录页面 .mvcMatchers("/login/**") 放行 /login?client_id=messaging-client，现在 .loginPage("/login") 永远不会触发
			.loginPage("/login") // loginPage 有两个功能 完全匹配放行 和 异常处理重定向 /login
			// 指定处理登录请求 URL
			.loginProcessingUrl("/oauth2/login")
			// 放行 loginPage 及 loginProcessingUrl
			.permitAll()
			.and()

			// 前后端分离登录
			// Spring Security 永远不会创建 HttpSession，也永远不会使用它来获取 SecurityContext
			//.sessionManagement()
			//.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			//.and()
			//将 token 验证过滤器 添加到 登录用户密码过滤器 之前执行
			//.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);


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

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		// @formatter:off
		return web ->
			web
				.ignoring()
				/*
				避免 favicon.ico 302 引发的重定向错误

				忽略后 favicon.ico 存在则没问题，不存在则重定向 /error 302 引发的重定向错误
				放行后 favicon.ico 不存在则重定向 /error 不会 302
					.antMatchers("/favicon.ico")
					.permitAll()

				WebSecurityCustomizer 的忽略比
					FilterSecurityInterceptor ->
						AbstractSecurityInterceptor ->
							AffirmativeBased ->
								WebExpressionVoter 放行

				更高一级，忽略后不会走 Spring Security 的过滤器
				需要 resources/static/favicon.ico 存在，否则会重定向 /error
			 	由于 /error 未被忽略放行，最后走了 FilterSecurityInterceptor 报 302 重定向 /login

			 	问题 400 如何被重定向到 /error 的？
			 		Spring Boot 404 重定向 /error
			 		FilterChainProxy#doFilterInternal 拦截 断点 可查看

				当然前后端分离不需要关心这些
				 */
				.antMatchers("/favicon.ico")
				.antMatchers("/error")
				;
		// @formatter:on
	}

	/**
	 * 暴露 Security 提供认证的接口
	 *
	 * @param authenticationConfiguration
	 * @return
	 * @throws Exception
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
