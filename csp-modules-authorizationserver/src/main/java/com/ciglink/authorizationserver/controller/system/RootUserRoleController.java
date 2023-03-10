package com.ciglink.authorizationserver.controller.system;

import cn.hutool.json.JSONUtil;
import com.ciglink.authorizationserver.domain.dto.RootUserRoleDto;
import com.ciglink.authorizationserver.service.RootUserRoleService;
import com.ciglink.common.core.web.result.AjaxResult;
import com.ciglink.common.web.entity.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WANGKairen
 * @since 2022-12-21 17:43:09
 **/
@RestController
@RequestMapping("/root/user/role")
public class RootUserRoleController extends BaseController<RootUserRoleDto, RootUserRoleService> {

    protected String getNodeName() {
        return "Root用户权限";
    }

    @GetMapping("/findAll")
    public AjaxResult findAll() {
        List<RootUserRoleDto> list = baseService.list();
        logger.info(getNodeName() + "：{}", JSONUtil.toJsonStr(list));
        return AjaxResult.success(list);
    }
}
