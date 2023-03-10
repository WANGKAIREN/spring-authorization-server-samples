package com.ciglink.authorizationserver.dao;

import com.ciglink.authorizationserver.domain.dto.RootUserRoleDto;
import com.ciglink.authorizationserver.mapper.RootUserRoleMapper;
import com.ciglink.common.web.entity.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * @author WANGKairen
 * @since 2022-12-21 17:26:44
 **/
@Component
public class RootUserRoleDao extends BaseDao<RootUserRoleDto, RootUserRoleMapper> {
}
