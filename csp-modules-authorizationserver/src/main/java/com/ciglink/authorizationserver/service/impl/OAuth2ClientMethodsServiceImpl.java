package com.ciglink.authorizationserver.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ciglink.authorizationserver.dao.OAuth2ClientMethodsDao;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientMethodsDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientMethodsMapper;
import com.ciglink.authorizationserver.service.OAuth2ClientMethodsService;
import com.ciglink.common.web.entity.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:02:08
 **/
@Service
public class OAuth2ClientMethodsServiceImpl extends BaseServiceImpl<OAuth2ClientMethodsDto, OAuth2ClientMethodsDao, OAuth2ClientMethodsMapper> implements OAuth2ClientMethodsService {

    @Override
    public Set<OAuth2ClientMethodsDto> findSetByClientId(String clientId) {
        LambdaQueryWrapper<OAuth2ClientMethodsDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2ClientMethodsDto::getClientId, clientId);
        return Convert.toSet(OAuth2ClientMethodsDto.class, list(queryWrapper));
    }
}
