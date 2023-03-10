package com.ciglink.authorizationserver.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciglink.authorizationserver.domain.po.RootUserRolePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WANGKairen
 * @since 2022-12-21 17:24:38
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("tmp_root_user_role")
public class RootUserRoleDto extends RootUserRolePo {

    private static final long serialVersionUID = 1L;
}
