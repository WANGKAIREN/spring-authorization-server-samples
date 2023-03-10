package com.ciglink.authorizationserver.service.impl;

import cn.hutool.core.lang.Assert;
import com.ciglink.authorizationserver.domain.dto.RootUserInfoDto;
import com.ciglink.authorizationserver.service.RootUserDetailsService;
import com.ciglink.authorizationserver.service.RootUserInfoService;
import com.ciglink.common.core.utils.SpringUtils;
import lombok.AllArgsConstructor;

/**
 * @author WANGKairen
 * @since 2022-12-26 15:56:27
 **/
@AllArgsConstructor
public class JdbcRootUserDetailsServiceImpl implements RootUserDetailsService {

    private final RootUserInfoService rootUserInfoService;

    //public JdbcRootUserDetailsServiceImpl() {
    //    rootUserInfoService = SpringUtils.getBean(RootUserInfoService.class);
    //    Assert.notNull(rootUserInfoService, "rootUserInfoService cannot be null");
    //}

    @Override
    public RootUserInfoDto doLoadRootUser(String username) {
        return rootUserInfoService.findByUsername(username);
    }
}
