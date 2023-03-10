package com.ciglink.authorizationserver.mapper;

import com.ciglink.authorizationserver.domain.dto.RootUserInfoDto;
import com.ciglink.common.web.entity.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WANGKairen
 * @since 2022-12-26 15:36:38
 **/
@Mapper
public interface RootUserInfoMapper extends BaseMapper<RootUserInfoDto> {
}
