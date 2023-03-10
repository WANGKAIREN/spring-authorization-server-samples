package com.ciglink.authorizationserver.dao;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientTokenSettingsDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientTokenSettingsMapper;
import com.ciglink.common.web.entity.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:33:04
 **/
@Component
public class OAuth2ClientTokenSettingsDao extends BaseDao<OAuth2ClientTokenSettingsDto, OAuth2ClientTokenSettingsMapper> {
}
