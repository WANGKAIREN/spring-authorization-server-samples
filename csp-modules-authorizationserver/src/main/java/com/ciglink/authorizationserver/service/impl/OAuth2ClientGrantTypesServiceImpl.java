package com.ciglink.authorizationserver.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ciglink.authorizationserver.dao.OAuth2ClientGrantTypesDao;
import com.ciglink.authorizationserver.domain.dto.OAuth2ClientGrantTypesDto;
import com.ciglink.authorizationserver.mapper.OAuth2ClientGrantTypesMapper;
import com.ciglink.authorizationserver.service.OAuth2ClientGrantTypesService;
import com.ciglink.common.web.entity.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author WANGKairen
 * @since 2022-12-27 18:02:16
 **/
@Service
public class OAuth2ClientGrantTypesServiceImpl extends BaseServiceImpl<OAuth2ClientGrantTypesDto, OAuth2ClientGrantTypesDao, OAuth2ClientGrantTypesMapper> implements OAuth2ClientGrantTypesService {
    @Override
    public Set<OAuth2ClientGrantTypesDto> findSetByClientId(String clientId) {
        LambdaQueryWrapper<OAuth2ClientGrantTypesDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OAuth2ClientGrantTypesDto::getClientId, clientId);
        return Convert.toSet(OAuth2ClientGrantTypesDto.class, list(queryWrapper));
    }
}
