package com.ciglink.authorizationserver.service;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientDto;
import com.ciglink.common.web.entity.service.IBaseService;

/**
 * @author WANGKairen
 * @since 2022-12-27 15:37:36
 **/
public interface OAuth2ClientService extends IBaseService<OAuth2ClientDto> {

    void insertJoin(OAuth2ClientDto oAuth2ClientDto);

    OAuth2ClientDto findJoinById(String id);

    OAuth2ClientDto findJoinByClientId(String clientId);
}
