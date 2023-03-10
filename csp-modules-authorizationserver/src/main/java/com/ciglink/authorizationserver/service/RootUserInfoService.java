package com.ciglink.authorizationserver.service;

import com.ciglink.authorizationserver.domain.dto.RootUserInfoDto;
import com.ciglink.common.web.entity.service.IBaseService;

/**
 * @author WANGKairen
 * @since 2022-12-26 15:39:00
 **/
public interface RootUserInfoService extends IBaseService<RootUserInfoDto> {

    RootUserInfoDto findByUsername(String username);
}
