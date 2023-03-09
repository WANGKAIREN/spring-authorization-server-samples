package com.ciglink.authcenter.authorizationserver.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciglink.authcenter.authorizationserver.domain.po.OAuth2ClientScopesPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:17:00
 **/
@EqualsAndHashCode(callSuper = true) // 等于 true 为了数据库 List 转 Set 去重，否则会被去重为 1 个 Dto 对象
@Data
@TableName("tmp_oauth2_client_scopes")
public class OAuth2ClientScopesDto extends OAuth2ClientScopesPo {

    private static final long serialVersionUID = 1L;
}
