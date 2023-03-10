package com.ciglink.authorizationserver.mapper;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientSettingsDto;
import com.ciglink.common.web.entity.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:29:05
 **/
@Mapper
public interface OAuth2ClientSettingsMapper extends BaseMapper<OAuth2ClientSettingsDto> {
}
