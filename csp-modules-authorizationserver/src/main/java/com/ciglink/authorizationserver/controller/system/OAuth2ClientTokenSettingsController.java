package com.ciglink.authorizationserver.controller.system;

import cn.hutool.json.JSONUtil;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientTokenSettingsDto;
import com.ciglink.authorizationserver.service.OAuth2ClientTokenSettingsService;
import com.ciglink.common.core.web.result.AjaxResult;
import com.ciglink.common.web.entity.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:04:49
 **/
@RestController
@RequestMapping("/oAuth2/client/token/settings")
public class OAuth2ClientTokenSettingsController extends BaseController<OAuth2ClientTokenSettingsDto, OAuth2ClientTokenSettingsService> {

    @Override
    protected String getNodeName() {
        return "OAuth2客户端Token配置";
    }

    @GetMapping("/findAll")
    public AjaxResult findAll() {
        List<OAuth2ClientTokenSettingsDto> list = baseService.list();
        logger.info(getNodeName() + "：{}", JSONUtil.toJsonStr(list));
        return AjaxResult.success(list);
    }
}
