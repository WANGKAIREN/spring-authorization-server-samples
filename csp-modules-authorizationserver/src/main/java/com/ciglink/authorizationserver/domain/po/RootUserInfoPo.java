package com.ciglink.authorizationserver.domain.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ciglink.common.core.web.entity.base.CommonBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author WANGKairen
 * @since 2022-12-23 11:01:56
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("Root用户信息")
public class RootUserInfoPo extends CommonBaseEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @TableField(value = "username", insertStrategy = FieldStrategy.NOT_NULL) // 在 insert 操作时此字段不能为空
    private String username;

    @JsonIgnore // 在 json 序列化时将此字段忽略掉
    @TableField(value = "password", insertStrategy = FieldStrategy.NOT_NULL) // 在 insert 操作时此字段不能为空
    private String password;

    private String nickName;

    private String realName;
    @TableField(value = "phone_number", insertStrategy = FieldStrategy.NOT_NULL) // 在 insert 操作时此字段不能为空
    private String phoneNumber;

    private String avatarUrl;

    private String email;

    private Integer gender;

    private Boolean enabled;

    //@ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    //@JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    //@ToString.Exclude
    //private Set<Role> authorities = Collections.emptySet();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet(); //TODO Role 权限 未实现
    }

    @Override
    public boolean isAccountNonExpired() {
        return getEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getEnabled();
    }

    @Override
    public boolean isEnabled() {
        return getEnabled();
    }
}
