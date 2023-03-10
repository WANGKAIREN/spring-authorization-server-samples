package com.ciglink.authorizationserver.dao;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientRedirectUrisDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientRedirectUrisMapper;
import com.ciglink.common.web.entity.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:32:47
 **/
@Component
public class OAuth2ClientRedirectUrisDao extends BaseDao<OAuth2ClientRedirectUrisDto, OAuth2ClientRedirectUrisMapper> {
}
