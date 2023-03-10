package com.ciglink.authorizationserver.mapper;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientRedirectUrisDto;
import com.ciglink.common.web.entity.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:28:40
 **/
@Mapper
public interface OAuth2ClientRedirectUrisMapper extends BaseMapper<OAuth2ClientRedirectUrisDto> {
}
