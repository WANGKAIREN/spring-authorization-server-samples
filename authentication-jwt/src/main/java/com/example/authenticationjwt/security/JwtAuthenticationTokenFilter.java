package com.example.authenticationjwt.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 利用 UsernamePasswordAuthenticationFilter 完成登录参数的提取
 *
 * @author WANGKairen
 * @since 2023-01-18 11:01:33
 **/
public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 执行实际身份验证
     *
     * @param request from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     * redirect as part of a multi-stage authentication process (such as OpenID).
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 必需 POST 请求方式
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        return super.attemptAuthentication(request, response);
    }
}
