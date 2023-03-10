package com.ciglink.authorizationserver.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciglink.authorizationserver.domain.po.RootUserInfoPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WANGKairen
 * @since 2022-12-26 11:02:25
 **/
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("tmp_root_user_info")
public class RootUserInfoDto extends RootUserInfoPo {

    private static final long serialVersionUID = 1L;
}
