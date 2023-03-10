package com.ciglink.authorizationserver.controller.system;

import cn.hutool.json.JSONUtil;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientScopesDto;
import com.ciglink.authorizationserver.service.OAuth2ClientScopesService;
import com.ciglink.common.core.web.result.AjaxResult;
import com.ciglink.common.web.entity.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:04:40
 **/
@RestController
@RequestMapping("/oAuth2/client/scopes")
public class OAuth2ClientScopesController extends BaseController<OAuth2ClientScopesDto, OAuth2ClientScopesService> {

    @Override
    protected String getNodeName() {
        return "OAuth2客户端授权范围";
    }

    @GetMapping("/findAll")
    public AjaxResult findAll() {
        List<OAuth2ClientScopesDto> list = baseService.list();
        logger.info(getNodeName() + "：{}", JSONUtil.toJsonStr(list));
        return AjaxResult.success(list);
    }
}
