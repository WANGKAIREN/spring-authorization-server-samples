package com.example.authenticationjwt.security;

import com.nimbusds.jose.JOSEException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * 登录认证检查过滤器
 * 拦截请求并判断token，继承BasicAuthenticationFilter此过滤器针对的是拥有Authorization头部信息的请求 咋们的token就在此存储，继承此过滤器对token进行处理
 *
 * @author Louis
 * @date Nov 20, 2018
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    	// 获取token, 并检查登录状态
        // 如果这里 校验失败，可以返回 Json
        try {
            Authentications.checkAuthentication(request);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        chain.doFilter(request, response);
    }
    
}