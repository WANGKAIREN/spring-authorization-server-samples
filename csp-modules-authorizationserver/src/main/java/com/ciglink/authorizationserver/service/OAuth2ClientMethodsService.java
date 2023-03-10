package com.ciglink.authorizationserver.service;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientMethodsDto;
import com.ciglink.common.web.entity.service.IBaseService;

import java.util.Set;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:01:22
 **/
public interface OAuth2ClientMethodsService extends IBaseService<OAuth2ClientMethodsDto> {

    Set<OAuth2ClientMethodsDto> findSetByClientId(String clientId);
}
