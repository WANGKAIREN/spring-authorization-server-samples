package com.ciglink.authcenter.authorizationserver.controller.oauth2;

import cn.hutool.core.util.StrUtil;
import com.ciglink.authcenter.authorizationserver.req.OAuth2UserInfoReq;
import com.ciglink.authcenter.authorizationserver.service.OAuth2LoginService;
import com.ciglink.common.core.web.result.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WANGKairen
 * @since 2023-01-30 19:06:10
 **/
@Slf4j
@RestController
public class OAuth2LoginController {

    @Autowired
    OAuth2LoginService oAuth2LoginService;

    @RequestMapping ("/oauth2/login/json")
    public AjaxResult login(@RequestBody OAuth2UserInfoReq user){
        String token = oAuth2LoginService.login(user);
        if (StrUtil.isEmpty(token)) {
            return AjaxResult.error("token生成失败");
        }
        log.info(token);
        return AjaxResult.success(token);
    }

    //@PostMapping("/loginOut")
    //public PublicDTO loginOut(){
    //
    //    return  loginService.loginOut();
    //
    //}
}
