package com.ciglink.authorizationserver.controller.system;

import cn.hutool.json.JSONUtil;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientRedirectUrisDto;
import com.ciglink.authorizationserver.service.OAuth2ClientRedirectUrisService;
import com.ciglink.common.core.web.result.AjaxResult;
import com.ciglink.common.web.entity.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:04:36
 **/
@RestController
@RequestMapping("/oAuth2/client/redirect/uris")
public class OAuth2ClientRedirectUrisController extends BaseController<OAuth2ClientRedirectUrisDto, OAuth2ClientRedirectUrisService> {

    @Override
    protected String getNodeName() {
        return "OAuth2客户端重定向地址";
    }

    @GetMapping("/findAll")
    public AjaxResult findAll() {
        List<OAuth2ClientRedirectUrisDto> list = baseService.list();
        logger.info(getNodeName() + "：{}", JSONUtil.toJsonStr(list));
        return AjaxResult.success(list);
    }
}
