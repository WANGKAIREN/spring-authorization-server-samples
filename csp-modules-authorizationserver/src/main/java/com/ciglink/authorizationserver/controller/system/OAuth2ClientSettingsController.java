package com.ciglink.authorizationserver.controller.system;

import cn.hutool.json.JSONUtil;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientSettingsDto;
import com.ciglink.authorizationserver.service.OAuth2ClientSettingsService;
import com.ciglink.common.core.web.result.AjaxResult;
import com.ciglink.common.web.entity.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:04:45
 **/
@RestController
@RequestMapping("/oAuth2/client/settings")
public class OAuth2ClientSettingsController extends BaseController<OAuth2ClientSettingsDto, OAuth2ClientSettingsService> {

    @Override
    protected String getNodeName() {
        return "OAuth2客户端配置";
    }

    @GetMapping("/findAll")
    public AjaxResult findAll() {
        List<OAuth2ClientSettingsDto> list = baseService.list();
        logger.info(getNodeName() + "：{}", JSONUtil.toJsonStr(list));
        return AjaxResult.success(list);
    }
}
