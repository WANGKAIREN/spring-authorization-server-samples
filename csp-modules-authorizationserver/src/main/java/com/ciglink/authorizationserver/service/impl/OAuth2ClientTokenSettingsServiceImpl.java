package com.ciglink.authorizationserver.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ciglink.authorizationserver.dao.OAuth2ClientTokenSettingsDao;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientSettingsDto;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientTokenSettingsDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientTokenSettingsMapper;
import com.ciglink.authorizationserver.service.OAuth2ClientTokenSettingsService;
import com.ciglink.common.web.entity.service.impl.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:02:39
 **/
@Service
@Slf4j
public class OAuth2ClientTokenSettingsServiceImpl extends BaseServiceImpl<OAuth2ClientTokenSettingsDto, OAuth2ClientTokenSettingsDao, OAuth2ClientTokenSettingsMapper> implements OAuth2ClientTokenSettingsService {

    @Override
    public OAuth2ClientTokenSettingsDto findSetByClientId(String clientId) {
        LambdaQueryWrapper<OAuth2ClientTokenSettingsDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2ClientTokenSettingsDto::getClientId, clientId);
        return getOne(queryWrapper);
    }

    public static void main(String[] args) {
        TokenSettings tokenSettings = new OAuth2ClientTokenSettingsDto().toTokenSettings();
        log.info("{}", JSONUtil.toJsonStr(tokenSettings));

        ClientSettings clientSettings = new OAuth2ClientSettingsDto().toClientSettings();
        log.info("{}", JSONUtil.toJsonStr(clientSettings));

    }
}
