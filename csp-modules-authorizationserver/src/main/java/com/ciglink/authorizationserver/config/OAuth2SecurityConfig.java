package com.ciglink.authorizationserver.config;

import com.ciglink.authorizationserver.security.LoginFilterSecurityConfigurer;
import com.ciglink.authorizationserver.security.RedirectLoginAuthenticationSuccessHandler;
import com.ciglink.authorizationserver.security.UnauthorizedEntryPoint;
import com.ciglink.authorizationserver.service.OAuth2ClientRegisteredService;
import com.ciglink.authorizationserver.service.OAuth2UserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

/**
 * @author WANGKairen
 * @since 2022-12-27 12:07:16
 **/
//@Configuration(proxyBeanMethods = false)
public class OAuth2SecurityConfig {

    /**
     * Default security filter chain security filter chain.
     *
     * @param http                     the http
     * @param oAuth2UserDetailsService the oauth2 user details service
     //* @param securityFilterChain      the security filter chain
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 2)
    SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity http
            //,OAuth2UserDetailsService oAuth2UserDetailsService
            ,@Qualifier("authorizationServerSecurityFilterChain") SecurityFilterChain securityFilterChain
    ) throws Exception {

        // TODO OAuth2 和 System 随便登录一个，都可以任意访问

        DefaultSecurityFilterChain authorizationServerFilterChain = (DefaultSecurityFilterChain) securityFilterChain;

        RedirectLoginAuthenticationSuccessHandler redirectLoginAuthenticationSuccessHandler = new RedirectLoginAuthenticationSuccessHandler();

        UnauthorizedEntryPoint unauthorizedEntryPoint = new UnauthorizedEntryPoint();
        AuthenticationEntryPointFailureHandler authenticationEntryPointFailureHandler = new AuthenticationEntryPointFailureHandler(unauthorizedEntryPoint);

        // @formatter:off
        http
            .requestMatcher(
                new AndRequestMatcher(
                    // 排除拦截的 URL
                    new NegatedRequestMatcher(new AntPathRequestMatcher(SystemSecurityConfig.SYSTEM_ANT_PATH))
                    ,new NegatedRequestMatcher(authorizationServerFilterChain.getRequestMatcher())
                    //,new NegatedRequestMatcher(new AntPathRequestMatcher("/login/**"))
                )
            )

            // 通过 URL 模式限制访问
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    //.mvcMatchers("/login","/oauth2/login.do").permitAll()
                    .anyRequest().authenticated()
            )

            .csrf()
            .disable()

            //.userDetailsService(oAuth2UserDetailsService::loadOAuth2UserByUsername)

            .formLogin()
            // 替换默认登录页面
            //.loginPage("/login")
            // 指定处理登录请求 URL
            //.loginProcessingUrl("/oauth2/login.do")

            //.successHandler(redirectLoginAuthenticationSuccessHandler)
            //.failureHandler(authenticationEntryPointFailureHandler)
            //.permitAll()
            //.and()

            //.apply(new LoginFilterSecurityConfigurer<>())

            // 手机号验证码登录模拟
            //.captchaLogin(captchaLoginConfigurer ->
            //    // 验证码校验 1 在此处配置 优先级最高 2 注册为Spring Bean 可以免配置
            //    captchaLoginConfigurer.captchaService(this::verifyCaptchaMock)
            //    // 根据手机号查询用户User Details  1 在此处配置 优先级最高 2 注册为Spring Bean 可以免配置
            //    .captchaUserDetailsService(this::loadUserByPhoneMock)
            //    // 两个登录保持一致
            //    .successHandler(redirectLoginAuthenticationSuccessHandler)
            //    // 两个登录保持一致
            //    .failureHandler(authenticationEntryPointFailureHandler)
            //)
        ;
        // @formatter:on

        return http.build();
    }


    //private boolean verifyCaptchaMock(String phone, String code) {
    //    return code.equals("1234");
    //}
    //
    //private UserDetails loadUserByPhoneMock(String phone) throws UsernameNotFoundException {
    //    return  // 用户名
    //            User.withUsername(phone)
    //                    // 密码
    //                    .password("password")
    //                    .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
    //                    .roles("user", "mobile")
    //                    .build();
    //}
}
