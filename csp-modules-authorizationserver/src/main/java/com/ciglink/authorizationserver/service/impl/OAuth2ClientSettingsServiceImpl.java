package com.ciglink.authorizationserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ciglink.authorizationserver.dao.OAuth2ClientSettingsDao;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientSettingsDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientSettingsMapper;
import com.ciglink.authorizationserver.service.OAuth2ClientSettingsService;
import com.ciglink.common.web.entity.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:02:33
 **/
@Service
public class OAuth2ClientSettingsServiceImpl extends BaseServiceImpl<OAuth2ClientSettingsDto, OAuth2ClientSettingsDao, OAuth2ClientSettingsMapper> implements OAuth2ClientSettingsService {

    @Override
    public OAuth2ClientSettingsDto findSetByClientId(String clientId) {
        LambdaQueryWrapper<OAuth2ClientSettingsDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2ClientSettingsDto::getClientId, clientId);
        return getOne(queryWrapper);
    }
}
