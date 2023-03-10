package com.ciglink.authorizationserver.controller.system;

import cn.hutool.json.JSONUtil;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientDto;
import com.ciglink.authorizationserver.service.OAuth2ClientService;
import com.ciglink.common.core.web.result.AjaxResult;
import com.ciglink.common.web.entity.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WANGKairen
 * @since 2022-12-27 15:42:41
 **/
@RestController
@RequestMapping("/system/oAuth2/client")
public class OAuth2ClientController extends BaseController<OAuth2ClientDto, OAuth2ClientService> {

    @Override
    protected String getNodeName() {
        return "OAuth2客户端";
    }

    @GetMapping("/findAll")
    public AjaxResult findAll() {
        List<OAuth2ClientDto> list = baseService.list();
        logger.info(getNodeName() + "：{}", JSONUtil.toJsonStr(list));
        return AjaxResult.success(list);
    }
}
