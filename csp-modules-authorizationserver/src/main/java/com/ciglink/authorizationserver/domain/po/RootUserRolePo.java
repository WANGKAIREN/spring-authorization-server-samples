package com.ciglink.authorizationserver.domain.po;

import com.ciglink.common.core.web.entity.base.CommonBaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WANGKairen
 * @since 2022-12-21 15:23:01
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("Root用户权限")
public class RootUserRolePo extends CommonBaseEntity {

    private static final long serialVersionUID = 1L;
    private Long rootUserInfoId;

    private Long rootUserRoleId;
}
