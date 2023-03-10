package com.ciglink.authorizationserver.enums;

/**
 * 临时 root user 用户
 * The enum Gender
 * TODO 后期删除
 *
 * @author WANGKairen
 * @since 2022-12-26 11:15:14
 **/
public enum RootUserInfoEnum {

    /**
     * Unknown gender.
     */
    ROOT_USE_ID("root_user_id", "root用户id"),
    ROOT_USERNAME("root", "root用户名"),
    /**
     * Female gender.
     */
    ROOT_RAW_PASSWORD("idserver", "root用户密码"),
    /**
     * Male gender.
     */
    ROOT_ROLE_NAME("id_server", "root用户角色");

    private final String code;
    private final String info;

    RootUserInfoEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
