package com.ciglink.authorizationserver.controller.system;

import cn.hutool.json.JSONUtil;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientGrantTypesDto;
import com.ciglink.authorizationserver.service.OAuth2ClientGrantTypesService;
import com.ciglink.common.core.web.result.AjaxResult;
import com.ciglink.common.web.entity.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:04:31
 **/
@RestController
@RequestMapping("/oAuth2/client/grant/types")
public class OAuth2ClientGrantTypesController extends BaseController<OAuth2ClientGrantTypesDto, OAuth2ClientGrantTypesService> {

    @Override
    protected String getNodeName() {
        return "OAuth2客户端授权方式";
    }

    @GetMapping("/findAll")
    public AjaxResult findAll() {
        List<OAuth2ClientGrantTypesDto> list = baseService.list();
        logger.info(getNodeName() + "：{}", JSONUtil.toJsonStr(list));
        return AjaxResult.success(list);
    }
}
