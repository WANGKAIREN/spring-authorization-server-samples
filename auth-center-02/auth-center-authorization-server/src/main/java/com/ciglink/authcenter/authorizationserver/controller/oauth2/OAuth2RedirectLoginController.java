package com.ciglink.authcenter.authorizationserver.controller.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

/**
 * @author WANGKairen
 * @since 2022-12-30 21:43:24
 **/
@Controller
public class OAuth2RedirectLoginController {

    /**
     * Oauth 2 login page string.
     *
     * @param model              the model
     * @param authentication     the authentication
     * @param enableCaptchaLogin the enable captcha login
     * @param csrfToken          the csrf token
     * @return the string
     */
    @GetMapping("/login")
    public String oauth2LoginPage(
        Model model,
        String client_id,
        @CurrentSecurityContext(expression = "authentication") Authentication authentication,
        @Value("${spring.security.oauth2.server.login.captcha.enabled:true}") boolean enableCaptchaLogin,
        @RequestAttribute(name = "org.springframework.security.web.csrf.CsrfToken", required = false) CsrfToken csrfToken
    ) {

        if (!(authentication instanceof AnonymousAuthenticationToken)){
            return "redirect:/";
        }
        if (csrfToken != null) {
            model.addAttribute("_csrfToken", csrfToken);
        }
        //SystemSettings systemSettings = new SystemSettings();
        model.addAttribute("enableCaptchaLogin",enableCaptchaLogin);
        //model.addAttribute("systemSettings", systemSettings);
        return "oauth2/login";
    }
}
