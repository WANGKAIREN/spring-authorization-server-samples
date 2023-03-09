package com.ciglink.authcenter.authorizationserver.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcClientMetadataClaimNames;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WANGKairen
 * @since 2023-01-13 09:49:56
 **/
@Slf4j
public class ClientLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public ClientLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    /**
     * 授权请求参数名
     * {@link OidcClientMetadataClaimNames}
     *
     * @param request the request
     * @param response the response
     * @param exception the exception
     * @return
     */
    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        String clientId = request.getParameter(OidcClientMetadataClaimNames.CLIENT_ID);
        String loginFormUrl = getLoginFormUrl() + "?" + OidcClientMetadataClaimNames.CLIENT_ID + "=" + clientId;
        log.info("loginFormUrl: {}", loginFormUrl);
        return loginFormUrl;
    }
}
