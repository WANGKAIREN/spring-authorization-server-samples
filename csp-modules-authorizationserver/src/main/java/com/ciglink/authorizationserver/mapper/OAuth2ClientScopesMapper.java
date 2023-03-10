package com.ciglink.authorizationserver.mapper;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientScopesDto;
import com.ciglink.common.web.entity.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:28:50
 **/
@Mapper
public interface OAuth2ClientScopesMapper extends BaseMapper<OAuth2ClientScopesDto> {
}
