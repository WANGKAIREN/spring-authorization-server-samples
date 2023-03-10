package com.ciglink.authorizationserver.mapper;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientGrantTypesDto;
import com.ciglink.common.web.entity.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:28:25
 **/
@Mapper
public interface OAuth2ClientGrantTypesMapper extends BaseMapper<OAuth2ClientGrantTypesDto> {
}
