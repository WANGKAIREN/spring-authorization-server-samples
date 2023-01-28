package com.example.authenticationjwt.security;

import com.example.authenticationjwt.advice.Rest;
import com.example.authenticationjwt.advice.RestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * OAuth2 用户认证成功处理
 * <br>
 * 可以在这里优化添加，用户认证成功后，保存到 Redis
 *
 * @author WANGKairen
 * @since 2023-01-22 14:19:27
 **/
@Slf4j
public class OAuth2UserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private RequestCache requestCache;
    private static final String defaultTargetUrl = "/";
    private final String redirect;

    public OAuth2UserAuthenticationSuccessHandler() {
        this(defaultTargetUrl, new HttpSessionRequestCache());
    }

    public OAuth2UserAuthenticationSuccessHandler(String redirect, RequestCache requestCache) {
        Assert.notNull(requestCache, "requestCache must not be null");
        this.redirect = redirect;
        this.requestCache = requestCache;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        SavedRequest savedRequest = this.requestCache.getRequest(request, response);

        String targetUrl = savedRequest == null ? this.redirect : savedRequest.getRedirectUrl();
        //clearAuthenticationAttributes(request);

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;

        Map<String, String> map = new HashMap<>();
        map.put("targetUrl", targetUrl);
        map.put("token", jwtAuthenticationToken.getToken());
        this.write(RestBody.okData(map, "登录成功！"), response);
    }

    private void write(Rest<?> body, HttpServletResponse response) throws IOException {
        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String resBody = objectMapper.writeValueAsString(body);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }

    @Override
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
