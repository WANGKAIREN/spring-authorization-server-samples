package com.ciglink.authorizationserver.mapper;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientDto;
import com.ciglink.common.web.entity.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WANGKairen
 * @since 2022-12-27 15:31:08
 **/
@Mapper
public interface OAuth2ClientMapper extends BaseMapper<OAuth2ClientDto> {
}
