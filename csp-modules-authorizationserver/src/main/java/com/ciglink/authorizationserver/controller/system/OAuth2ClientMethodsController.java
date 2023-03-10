package com.ciglink.authorizationserver.controller.system;

import cn.hutool.json.JSONUtil;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientMethodsDto;
import com.ciglink.authorizationserver.service.OAuth2ClientMethodsService;
import com.ciglink.common.core.web.result.AjaxResult;
import com.ciglink.common.web.entity.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:04:25
 **/
@RestController
@RequestMapping("/oAuth2/client/methods")
public class OAuth2ClientMethodsController extends BaseController<OAuth2ClientMethodsDto, OAuth2ClientMethodsService> {

    @Override
    protected String getNodeName() {
        return "OAuth2客户端认证方式";
    }

    @GetMapping("/findAll")
    public AjaxResult findAll() {
        List<OAuth2ClientMethodsDto> list = baseService.list();
        logger.info(getNodeName() + "：{}", JSONUtil.toJsonStr(list));
        return AjaxResult.success(list);
    }
}
