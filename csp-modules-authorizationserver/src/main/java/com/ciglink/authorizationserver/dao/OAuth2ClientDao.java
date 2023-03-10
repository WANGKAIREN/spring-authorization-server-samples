package com.ciglink.authorizationserver.dao;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientMapper;
import com.ciglink.common.web.entity.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * @author WANGKairen
 * @since 2022-12-27 15:31:28
 **/
@Component
public class OAuth2ClientDao extends BaseDao<OAuth2ClientDto, OAuth2ClientMapper> {
}
