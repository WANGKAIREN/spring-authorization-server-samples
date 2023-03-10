package com.ciglink.authorizationserver.service;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientRedirectUrisDto;
import com.ciglink.common.web.entity.service.IBaseService;

import java.util.Set;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:01:36
 **/
public interface OAuth2ClientRedirectUrisService extends IBaseService<OAuth2ClientRedirectUrisDto> {

    Set<OAuth2ClientRedirectUrisDto> findSetByClientId(String clientId);
}
