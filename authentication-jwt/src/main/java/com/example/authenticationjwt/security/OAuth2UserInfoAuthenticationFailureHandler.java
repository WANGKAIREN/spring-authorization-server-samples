package com.example.authenticationjwt.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author WANGKairen
 * @since 2023-01-22 14:48:08
 **/
public class OAuth2UserInfoAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Example1(request, response, exception);
//		Example2(request,response,exception);
//        Example3(request,response,exception);
    }

    private void Example1(HttpServletRequest request, HttpServletResponse response,
                          AuthenticationException exception) throws IOException, ServletException {
        System.out.println(exception.getClass());
        if (exception instanceof BadCredentialsException) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println("{\"state\":205,\"msg\":\"密码错误\"}");
        } else if (exception instanceof AuthenticationServiceException) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println("{\"state\":206,\"msg\":\"没有此用户\"}");
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println("{\"state\":204,\"msg\":\"登录失败\"}");
        }
        //例1：直接返回字符串

    }

    private void Example2(HttpServletRequest request, HttpServletResponse response,
                          AuthenticationException exception) throws IOException, ServletException {
        String strUrl = request.getContextPath() + "/customLoginResponse.jsp";
        request.getSession().setAttribute("ok", 0);
        request.getSession().setAttribute("message", exception.getLocalizedMessage());
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        super.onAuthenticationFailure(request, response, exception);
    }

    private void Example3(HttpServletRequest request, HttpServletResponse response,
                          AuthenticationException exception) throws IOException, ServletException {
        //例3：自定义跳转到哪个URL
        //假设login.jsp在webapp路径下
        //注意：不能访问WEB-INF下的jsp。
        String strUrl = request.getContextPath() + "/customLoginResponse.jsp";
        request.getSession().setAttribute("ok", 0);
        request.getSession().setAttribute("message", exception.getLocalizedMessage());
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        //Error  request.getRequestDispatcher(strUrl).forward(request, response);
        response.sendRedirect(strUrl);
    }
}
