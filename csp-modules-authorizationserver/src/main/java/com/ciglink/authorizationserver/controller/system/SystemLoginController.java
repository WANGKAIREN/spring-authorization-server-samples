package com.ciglink.authorizationserver.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author WANGKairen
 * @since 2022-12-30 19:01:15
 **/
@Controller
public class SystemLoginController {

    @GetMapping("/system/login")
    public String loginPage(
            //Model model,
            //@CurrentSecurityContext(expression = "authentication") Authentication authentication,
            //@RequestAttribute(name = "org.springframework.security.web.csrf.CsrfToken", required = false) CsrfToken csrfToken
    ) {

        //if (!(authentication instanceof AnonymousAuthenticationToken)){
        //    return "redirect:/system";
        //}
        //if (csrfToken != null) {
        //    model.addAttribute("_csrfToken", csrfToken);
        //}
        //SystemSettings systemSettings = new SystemSettings();
        //model.addAttribute("systemSettings", systemSettings);
        return "/system/login";
    }
}
