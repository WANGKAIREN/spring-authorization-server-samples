package com.ciglink.authcenter.authorizationserver.domain.po;

import com.ciglink.common.core.web.entity.base.CommonBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Root用户权限
 *
 * @author WANGKairen
 * @since 2022-12-21 15:23:01
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class RootUserRolePo extends CommonBaseEntity {

    private static final long serialVersionUID = 1L;

    private Long rootUserInfoId;

    private Long rootUserRoleId;
}
