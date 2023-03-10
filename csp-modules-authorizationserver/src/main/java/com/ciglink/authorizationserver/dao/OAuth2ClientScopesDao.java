package com.ciglink.authorizationserver.dao;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientScopesDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientScopesMapper;
import com.ciglink.common.web.entity.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:32:52
 **/
@Component
public class OAuth2ClientScopesDao extends BaseDao<OAuth2ClientScopesDto, OAuth2ClientScopesMapper> {
}
