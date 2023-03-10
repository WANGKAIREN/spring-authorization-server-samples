package com.ciglink.authorizationserver.service;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientGrantTypesDto;
import com.ciglink.common.web.entity.service.IBaseService;

import java.util.Set;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:01:29
 **/
public interface OAuth2ClientGrantTypesService extends IBaseService<OAuth2ClientGrantTypesDto> {

    Set<OAuth2ClientGrantTypesDto> findSetByClientId(String clientId);
}
