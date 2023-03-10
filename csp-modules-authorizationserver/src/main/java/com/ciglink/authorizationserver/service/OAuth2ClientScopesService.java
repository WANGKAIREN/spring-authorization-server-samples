package com.ciglink.authorizationserver.service;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientScopesDto;
import com.ciglink.common.web.entity.service.IBaseService;

import java.util.Set;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:01:43
 **/
public interface OAuth2ClientScopesService extends IBaseService<OAuth2ClientScopesDto> {

    Set<OAuth2ClientScopesDto> findSetByClientId(String clientId);
}
