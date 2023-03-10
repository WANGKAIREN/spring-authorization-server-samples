package com.ciglink.authorizationserver.config;

import com.ciglink.authorizationserver.security.RedirectLoginAuthenticationSuccessHandler;
import com.ciglink.authorizationserver.security.SystemJsonAuthenticationSuccessHandler;
import com.ciglink.authorizationserver.security.UnauthorizedEntryPoint;
import com.ciglink.authorizationserver.service.RootUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

/**
 * 系统资源
 * 拦截系统资源相关的请求端点
 *
 * @author WANGKairen
 * @since 2022-12-27 12:06:43
 **/
//@Configuration(proxyBeanMethods = false)
public class SystemSecurityConfig {

    protected static final String SYSTEM_ANT_PATH = "/system/**";

    public static final String AUTHORIZATIONSERVER_SYSTEM_SECURITY_SAVED_REQUEST_KEY = "AUTHORIZATIONSERVER_SYSTEM_SECURITY_SAVED_REQUEST";

    public static final String AUTHORIZATIONSERVER_SYSTEM_SECURITY_CONTEXT_KEY = "AUTHORIZATIONSERVER_SYSTEM_SECURITY_CONTEXT";

    /**
     * 管理后台以{@code /system}开头
     *
     * @param http                the http
     * @param rootUserDetailsService the root user details service
     * @return the security filter chain
     * @throws Exception the exception
     * @see AuthorizationServerConfig
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE + 1)
    SecurityFilterChain systemSecurityFilterChain(HttpSecurity http, RootUserDetailsService rootUserDetailsService) throws Exception {

        // Test
        //http://localhost:9500/system/oAuth2/client/findAll
        //http://localhost:9500/oAuth2/client/grant/types/findAll


        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setSessionAttrName(AUTHORIZATIONSERVER_SYSTEM_SECURITY_SAVED_REQUEST_KEY); // 目的不明

        UnauthorizedEntryPoint unauthorizedEntryPoint = new UnauthorizedEntryPoint();
        AuthenticationEntryPointFailureHandler authenticationEntryPointFailureHandler = new AuthenticationEntryPointFailureHandler(unauthorizedEntryPoint);

        HttpSessionSecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
        securityContextRepository.setSpringSecurityContextKey(AUTHORIZATIONSERVER_SYSTEM_SECURITY_CONTEXT_KEY); // 目的不明

        // @formatter:off
        http
            // 只拦截匹配 URL
            .antMatcher(SYSTEM_ANT_PATH)

            // 关闭跨站请求攻击保护
            .csrf()
            .disable()

            // HTTP 响应头 X-Frame-Options
            //.headers()
            //.frameOptions()
            //.sameOrigin() // 只要是同源，可以在 <frame>、<iframe>、<object> 中显示 html
            //.and()

            // 缓存请求
            //.requestCache()
            //.requestCache(requestCache)
            //.and()

            // HttpSession 中保存安全上下文 security context，这样属于同一个 HttpSession 的多个请求，就能够利用此机制访问同一安全上下文
            // 两套独立认证，OAuth2 不用独立，因为要和授权服务器共用，System 不应该和授权服务器共用
            .securityContext()
            .securityContextRepository(securityContextRepository)
            .and()

            // 通过 URL 模式限制访问
            .authorizeRequests()

            // 设置 successHandler，就不用放开以下 URL
            //.mvcMatchers("/system/login")
            //.permitAll()

            .anyRequest()
            .authenticated()
            .and()

            //
            //.exceptionHandling()
            //.authenticationEntryPoint(unauthorizedEntryPoint)
            //.and()

            // Root 用户
            .userDetailsService(rootUserDetailsService::loadRootUserByUsername)
            .formLogin()
            //.disable()

            // 替换默认登录页面
            .loginPage("/system/login")
            // 指定处理登录请求 URL
            .loginProcessingUrl("/system/login.do")

            // 登录成功后的逻辑，前后端分离返回 Json
            .successHandler(new RedirectLoginAuthenticationSuccessHandler("/system", requestCache))
            //.successHandler(new SystemJsonAuthenticationSuccessHandler(requestCache))
            // 登录失败后的逻辑，前后端分离返回 Json
            .failureHandler(authenticationEntryPointFailureHandler)
            .permitAll()
        ;
        // @formatter:on

        return http.build();
    }

}
