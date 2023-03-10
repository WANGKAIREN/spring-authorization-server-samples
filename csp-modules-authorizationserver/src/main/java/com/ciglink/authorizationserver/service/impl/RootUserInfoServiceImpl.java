package com.ciglink.authorizationserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ciglink.authorizationserver.dao.RootUserInfoDao;
import com.ciglink.authorizationserver.domain.dto.RootUserInfoDto;
import com.ciglink.authorizationserver.mapper.RootUserInfoMapper;
import com.ciglink.authorizationserver.service.RootUserInfoService;
import com.ciglink.common.web.entity.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author WANGKairen
 * @since 2022-12-26 15:40:05
 **/
@Service
public class RootUserInfoServiceImpl extends BaseServiceImpl<RootUserInfoDto, RootUserInfoDao, RootUserInfoMapper> implements RootUserInfoService {

    @Override
    public RootUserInfoDto findByUsername(String username) {
        LambdaQueryWrapper<RootUserInfoDto> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RootUserInfoDto::getUsername, username);
        return getOne(queryWrapper);
    }
}
