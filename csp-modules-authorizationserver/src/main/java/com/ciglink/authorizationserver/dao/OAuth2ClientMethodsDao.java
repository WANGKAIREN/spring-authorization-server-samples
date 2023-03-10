package com.ciglink.authorizationserver.dao;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientMethodsDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientMethodsMapper;
import com.ciglink.common.web.entity.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:32:30
 **/
@Component
public class OAuth2ClientMethodsDao extends BaseDao<OAuth2ClientMethodsDto, OAuth2ClientMethodsMapper> {
}
