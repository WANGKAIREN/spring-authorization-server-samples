package com.ciglink.authorizationserver.dao;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientGrantTypesDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientGrantTypesMapper;
import com.ciglink.common.web.entity.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:32:41
 **/
@Component
public class OAuth2ClientGrantTypesDao extends BaseDao<OAuth2ClientGrantTypesDto, OAuth2ClientGrantTypesMapper> {
}
