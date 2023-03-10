package com.ciglink.authorizationserver.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ciglink.authorizationserver.dao.OAuth2ClientRedirectUrisDao;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientGrantTypesDto;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientRedirectUrisDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientRedirectUrisMapper;
import com.ciglink.authorizationserver.service.OAuth2ClientRedirectUrisService;
import com.ciglink.common.web.entity.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:02:21
 **/
@Service
public class OAuth2ClientRedirectUrisServiceImpl extends BaseServiceImpl<OAuth2ClientRedirectUrisDto, OAuth2ClientRedirectUrisDao, OAuth2ClientRedirectUrisMapper> implements OAuth2ClientRedirectUrisService {

    @Override
    public Set<OAuth2ClientRedirectUrisDto> findSetByClientId(String clientId) {
        LambdaQueryWrapper<OAuth2ClientRedirectUrisDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2ClientRedirectUrisDto::getClientId, clientId);
        return Convert.toSet(OAuth2ClientRedirectUrisDto.class, list(queryWrapper));
    }
}
