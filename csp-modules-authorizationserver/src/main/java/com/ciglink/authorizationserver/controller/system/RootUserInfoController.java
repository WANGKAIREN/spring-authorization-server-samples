package com.ciglink.authorizationserver.controller.system;

import cn.hutool.json.JSONUtil;
import com.ciglink.authorizationserver.domain.dto.RootUserInfoDto;
import com.ciglink.authorizationserver.service.RootUserInfoService;
import com.ciglink.common.core.web.result.AjaxResult;
import com.ciglink.common.web.entity.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WANGKairen
 * @since 2022-12-26 15:41:29
 **/
@RestController
@RequestMapping("/root/user/info")
public class RootUserInfoController extends BaseController<RootUserInfoDto, RootUserInfoService> {
    @Override
    protected String getNodeName() {
        return "Root用户信息";
    }

    @GetMapping("/findAll")
    public AjaxResult findAll() {
        List<RootUserInfoDto> list = baseService.list();
        logger.info(getNodeName() + "：{}", JSONUtil.toJsonStr(list));
        return AjaxResult.success(list);
    }
}
