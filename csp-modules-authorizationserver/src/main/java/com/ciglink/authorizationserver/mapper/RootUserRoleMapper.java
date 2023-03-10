package com.ciglink.authorizationserver.mapper;

import com.ciglink.authorizationserver.domain.dto.RootUserRoleDto;
import com.ciglink.common.web.entity.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WANGKairen
 * @since 2022-12-21 17:29:37
 **/
@Mapper
public interface RootUserRoleMapper extends BaseMapper<RootUserRoleDto> {
}
