package com.ciglink.authorizationserver.service;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientTokenSettingsDto;
import com.ciglink.common.web.entity.service.IBaseService;

import java.util.Set;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:01:55
 **/
public interface OAuth2ClientTokenSettingsService extends IBaseService<OAuth2ClientTokenSettingsDto> {

    OAuth2ClientTokenSettingsDto findSetByClientId(String clientId);
}
