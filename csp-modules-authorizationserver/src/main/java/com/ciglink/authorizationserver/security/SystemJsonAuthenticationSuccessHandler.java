package com.ciglink.authorizationserver.security;

import com.ciglink.authorizationserver.advice.Rest;
import com.ciglink.authorizationserver.advice.RestBody;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * TODO 待重写
 * 前后端分离，系统资源认证成功后返回 Json
 *
 * @author WANGKairen
 * @since 2022-12-30 20:21:58
 **/
public class SystemJsonAuthenticationSuccessHandler extends ResponseWriter implements AuthenticationSuccessHandler {

    private RequestCache requestCache;

    public SystemJsonAuthenticationSuccessHandler() {
        this(new HttpSessionRequestCache());
    }

    public SystemJsonAuthenticationSuccessHandler(RequestCache requestCache) {
        Assert.notNull(requestCache,"requestCache must not be null");
        this.requestCache = requestCache;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        clearAuthenticationAttributes(request);
        write(request, response);
    }

    @Override
    protected Rest<?> body(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<>(1);
        SavedRequest savedRequest = this.requestCache.getRequest(request, null);
        String targetUrl = savedRequest.getRedirectUrl();
        map.put("uri", request.getRequestURI());
        map.put("targetUrl", targetUrl);
        String exMsg = (String) request.getAttribute("exMsg");
        return RestBody.build(HttpStatus.UNAUTHORIZED.value(), map, exMsg, false);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
