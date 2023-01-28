package com.example.authenticationjwt.security;

import com.example.authenticationjwt.jose.Jwss;
import com.example.authenticationjwt.utils.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogMessage;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 利用 UsernamePasswordAuthenticationFilter 完成登录参数的提取
 *
 * @author WANGKairen
 * @since 2023-01-18 11:01:33
 **/
@Slf4j
//@Component
public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    //@Autowired
    private UserDetailsService userDetailsService;

    /**
     * 执行实际身份验证
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

         /*
         可以在此覆写尝试进行登录认证的逻辑，登录成功之后等操作不再此方法内
         如果使用此过滤器来触发登录认证流程，注意登录请求数据格式的问题
         此过滤器的用户名密码默认从 request.getParameter() 获取
         但是这种读取方式不能读取到如 application/json 等 post 请求数据
         需要把用户名密码的读取逻辑修改为到流中读取 request.getInputStream()
         */

        // 判断是否是 POST 请求方式
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 判断是否是 JSON 格式请求类型
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            try {
                // @formatter:off
                // 从前端提交 JSON 格式数据中获取用户输入的用户名和密码 {"username":"xxx","password":"xxx"}
                Map<String, String> userInfo = JacksonUtil.getInstance().readValue(request.getInputStream(), new TypeReference<Map<String, String>>() {});
                // @formatter:on

                String username = userInfo.get(getUsernameParameter());
                String password = userInfo.get(getPasswordParameter());

                log.info("username: {}, password: {}", username, password);

                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                // Allow subclasses to set the "details" property
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new AuthenticationServiceException("Authentication input stream i/o exception: ", e);
            }
        }

        // 如果不是 JSON 格式，那么按照父类的表单登录逻辑处理
        return super.attemptAuthentication(request, response);
    }

    /**
     * 登录成功处理
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        // 存储认证信息到上下文
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        if (logger.isDebugEnabled()) {
            logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
        }

        // 记住我服务
        getRememberMeServices().loginSuccess(request, response, authResult);

        // 触发事件监听器
        if (eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, getClass()));
        }

        // 生成并返回token给客户端，后续访问携带此token
        String username = retrieveUserName(authResult);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 保存
        String token = null;
        try {
            token = Jwss.generateToken(authResult, "userDetails.getUserId()");
        } catch (JOSEException e) {
            log.error("签名格式化异常", e);
        }
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(null, null, token);
        //jwtAuthenticationToken.setUserKey(byUsername.getId());

        //MyAuthenticationSuccessHandler myAuthenticationSuccessHandler = new MyAuthenticationSuccessHandler();
        //myAuthenticationSuccessHandler.onAuthenticationSuccess(request,response,jwtAuthenticationToken);
    }

    /**
     * 登录失败处理
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    protected String retrieveUserName(Authentication authentication) {
        if (isInstanceOfUserDetails(authentication)) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return authentication.getPrincipal().toString();
    }

    private boolean isInstanceOfUserDetails(Authentication authentication) {
        return authentication.getPrincipal() instanceof UserDetails;
    }
}
