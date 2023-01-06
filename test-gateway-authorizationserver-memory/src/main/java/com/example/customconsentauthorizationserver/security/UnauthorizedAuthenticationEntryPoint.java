package com.example.customconsentauthorizationserver.security;

import com.example.customconsentauthorizationserver.advice.ResponseWriter;
import com.example.customconsentauthorizationserver.advice.Rest;
import com.example.customconsentauthorizationserver.advice.RestBody;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 未经授权的认证端口
 *
 * @author WANGKairen
 * @since 2023-01-06 11:36:19
 **/
public class UnauthorizedAuthenticationEntryPoint extends ResponseWriter implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message = exceptionMessage(authException);
        request.setAttribute("exMsg", message);
        write(request, response);
    }

    @Override
    protected Rest<?> body(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<>(1);
        map.put("uri", request.getRequestURI());
        String exMsg = (String) request.getAttribute("exMsg");
        return RestBody.build(HttpStatus.UNAUTHORIZED.value(), map, exMsg, false);
    }

    private String exceptionMessage(AuthenticationException exception) {
        String msg;
        if (exception instanceof InsufficientAuthenticationException) {
            msg = "需要完全身份认证才能访问此资源";
        }else if (exception instanceof AccountExpiredException) {
            msg = "账户过期";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            msg = "用户身份凭证未找到";
        } else if (exception instanceof AuthenticationServiceException) {
            msg = "用户身份认证服务异常";
        } else if (exception instanceof BadCredentialsException) {
            msg = exception.getMessage();
        } else {
            msg = exception.getClass() + ": " + exception.getMessage() + " 未知异常待识别";
        }
        return msg;
    }
}
