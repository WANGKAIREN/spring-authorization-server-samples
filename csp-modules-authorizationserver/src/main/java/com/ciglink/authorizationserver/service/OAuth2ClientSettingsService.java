package com.ciglink.authorizationserver.service;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientSettingsDto;
import com.ciglink.common.web.entity.service.IBaseService;

import java.util.Set;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:01:48
 **/
public interface OAuth2ClientSettingsService extends IBaseService<OAuth2ClientSettingsDto> {

    OAuth2ClientSettingsDto findSetByClientId(String clientId);
}
