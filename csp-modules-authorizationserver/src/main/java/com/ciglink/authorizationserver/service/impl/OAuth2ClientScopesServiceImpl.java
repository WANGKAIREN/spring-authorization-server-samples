package com.ciglink.authorizationserver.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ciglink.authorizationserver.dao.OAuth2ClientScopesDao;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientScopesDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientScopesMapper;
import com.ciglink.authorizationserver.service.OAuth2ClientScopesService;
import com.ciglink.common.web.entity.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:02:28
 **/
@Service
public class OAuth2ClientScopesServiceImpl extends BaseServiceImpl<OAuth2ClientScopesDto, OAuth2ClientScopesDao, OAuth2ClientScopesMapper> implements OAuth2ClientScopesService {

    @Override
    public Set<OAuth2ClientScopesDto> findSetByClientId(String clientId) {
        LambdaQueryWrapper<OAuth2ClientScopesDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2ClientScopesDto::getClientId, clientId);
        return Convert.toSet(OAuth2ClientScopesDto.class, list(queryWrapper));
    }
}
