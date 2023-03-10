package com.ciglink.authorizationserver.service.impl;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ciglink.authorizationserver.dao.OAuth2ClientDao;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientDto;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientSettingsDto;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientTokenSettingsDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientMapper;
import com.ciglink.authorizationserver.service.OAuth2ClientGrantTypesService;
import com.ciglink.authorizationserver.service.OAuth2ClientMethodsService;
import com.ciglink.authorizationserver.service.OAuth2ClientRedirectUrisService;
import com.ciglink.authorizationserver.service.OAuth2ClientScopesService;
import com.ciglink.authorizationserver.service.OAuth2ClientService;
import com.ciglink.authorizationserver.service.OAuth2ClientSettingsService;
import com.ciglink.authorizationserver.service.OAuth2ClientTokenSettingsService;
import com.ciglink.common.web.entity.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

/**
 * @author WANGKairen
 * @since 2022-12-27 15:37:51
 **/
@Service
public class OAuth2ClientServiceImpl extends BaseServiceImpl<OAuth2ClientDto, OAuth2ClientDao, OAuth2ClientMapper> implements OAuth2ClientService {

    @Autowired
    private OAuth2ClientMethodsService oAuth2ClientMethodsService;

    @Autowired
    private OAuth2ClientGrantTypesService oAuth2ClientGrantTypesService;

    @Autowired
    private OAuth2ClientRedirectUrisService oAuth2ClientRedirectUrisService;

    @Autowired
    private OAuth2ClientScopesService oAuth2ClientScopesService;

    @Autowired
    private OAuth2ClientSettingsService oAuth2ClientSettingsService;

    @Autowired
    private OAuth2ClientTokenSettingsService oAuth2ClientTokenSettingsService;


    @Override
    @DSTransactional // TODO 需要测试
    public void insertJoin(OAuth2ClientDto oAuth2ClientDto) {
        insert(oAuth2ClientDto);
        oAuth2ClientMethodsService.insertBatch(oAuth2ClientDto.getClientAuthenticationMethods());
        oAuth2ClientGrantTypesService.insertBatch(oAuth2ClientDto.getAuthorizationGrantTypes());
        oAuth2ClientRedirectUrisService.insertBatch(oAuth2ClientDto.getRedirectUris());
        oAuth2ClientScopesService.insertBatch(oAuth2ClientDto.getScopes());
        oAuth2ClientSettingsService.insert(oAuth2ClientDto.getClientSettings());
        oAuth2ClientTokenSettingsService.insert(oAuth2ClientDto.getTokenSettings());
    }

    @Override
    public OAuth2ClientDto findJoinById(String id) {
        OAuth2ClientDto oAuth2ClientDto = selectById(id);
        oAuth2ClientDto.setClientAuthenticationMethods(oAuth2ClientMethodsService.findSetByClientId(oAuth2ClientDto.getClientId()));
        oAuth2ClientDto.setAuthorizationGrantTypes(oAuth2ClientGrantTypesService.findSetByClientId(oAuth2ClientDto.getClientId()));
        oAuth2ClientDto.setRedirectUris(oAuth2ClientRedirectUrisService.findSetByClientId(oAuth2ClientDto.getClientId()));
        oAuth2ClientDto.setScopes(oAuth2ClientScopesService.findSetByClientId(oAuth2ClientDto.getClientId()));
        oAuth2ClientDto.setClientSettings(oAuth2ClientSettingsService.findSetByClientId(oAuth2ClientDto.getClientId()));
        oAuth2ClientDto.setTokenSettings(oAuth2ClientTokenSettingsService.findSetByClientId(oAuth2ClientDto.getClientId()));
        return oAuth2ClientDto;
    }

    @Override
    public OAuth2ClientDto findJoinByClientId(String clientId) {
        LambdaQueryWrapper<OAuth2ClientDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2ClientDto::getClientId, clientId);
        OAuth2ClientDto oAuth2ClientDto = getOne(queryWrapper);
        oAuth2ClientDto.setClientAuthenticationMethods(oAuth2ClientMethodsService.findSetByClientId(oAuth2ClientDto.getClientId()));
        oAuth2ClientDto.setAuthorizationGrantTypes(oAuth2ClientGrantTypesService.findSetByClientId(oAuth2ClientDto.getClientId()));
        oAuth2ClientDto.setRedirectUris(oAuth2ClientRedirectUrisService.findSetByClientId(oAuth2ClientDto.getClientId()));
        oAuth2ClientDto.setScopes(oAuth2ClientScopesService.findSetByClientId(oAuth2ClientDto.getClientId()));
        //oAuth2ClientDto.setClientSettings(oAuth2ClientSettingsService.findSetByClientId(oAuth2ClientDto.getClientId()));
        //oAuth2ClientDto.setTokenSettings(oAuth2ClientTokenSettingsService.findSetByClientId(oAuth2ClientDto.getClientId()));

        // TODO 临时，这段代码应该添加到保存中
        OAuth2ClientSettingsDto oAuth2ClientSettingsDto = oAuth2ClientSettingsService.findSetByClientId(oAuth2ClientDto.getClientId());
        if (oAuth2ClientSettingsDto == null) {
            oAuth2ClientSettingsDto = OAuth2ClientSettingsDto.fromClientSettings(new OAuth2ClientSettingsDto().toClientSettings());
        }
        oAuth2ClientDto.setClientSettings(oAuth2ClientSettingsDto);

        OAuth2ClientTokenSettingsDto oAuth2ClientTokenSettingsDto = oAuth2ClientTokenSettingsService.findSetByClientId(oAuth2ClientDto.getClientId());
        if (oAuth2ClientTokenSettingsDto == null) {
            oAuth2ClientTokenSettingsDto = OAuth2ClientTokenSettingsDto.fromTokenSettings(new OAuth2ClientTokenSettingsDto().toTokenSettings());
        }
        oAuth2ClientDto.setTokenSettings(oAuth2ClientTokenSettingsDto);
        return oAuth2ClientDto;
    }
}
