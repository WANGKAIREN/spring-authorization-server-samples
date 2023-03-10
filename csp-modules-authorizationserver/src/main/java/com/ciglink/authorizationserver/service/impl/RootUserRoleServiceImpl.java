package com.ciglink.authorizationserver.service.impl;

import com.ciglink.authorizationserver.dao.RootUserRoleDao;
import com.ciglink.authorizationserver.domain.dto.RootUserRoleDto;
import com.ciglink.authorizationserver.mapper.RootUserRoleMapper;
import com.ciglink.authorizationserver.service.RootUserRoleService;
import com.ciglink.common.web.entity.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author WANGKairen
 * @since 2022-12-21 17:39:07
 **/
@Service
public class RootUserRoleServiceImpl extends BaseServiceImpl<RootUserRoleDto, RootUserRoleDao, RootUserRoleMapper> implements RootUserRoleService {
}
