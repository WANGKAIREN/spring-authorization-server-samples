package com.ciglink.authorizationserver.dao;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientSettingsDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientSettingsMapper;
import com.ciglink.common.web.entity.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:32:58
 **/
@Component
public class OAuth2ClientSettingsDao extends BaseDao<OAuth2ClientSettingsDto, OAuth2ClientSettingsMapper> {
}
