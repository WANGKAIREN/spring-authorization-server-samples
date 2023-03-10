package com.ciglink.authorizationserver.dao;

import com.ciglink.authorizationserver.domain.dto.RootUserInfoDto;
import com.ciglink.authorizationserver.mapper.RootUserInfoMapper;
import com.ciglink.common.web.entity.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * @author WANGKairen
 * @since 2022-12-26 15:36:18
 **/
@Component
public class RootUserInfoDao extends BaseDao<RootUserInfoDto, RootUserInfoMapper> {
}
