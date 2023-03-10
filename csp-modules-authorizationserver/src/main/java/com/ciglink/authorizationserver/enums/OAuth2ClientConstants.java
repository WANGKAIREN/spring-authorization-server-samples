package com.ciglink.authorizationserver.enums;

/**
 * @author WANGKairen
 * @since 2022-12-28 14:46:32
 **/
public class OAuth2ClientConstants {

    public enum DictType {

        SYS_NORMAL_DISABLE("sys_normal_disable", "系统开关列表"),
        SYS_SHOW_HIDE("sys_show_hide", "常规：显隐列表"),
        SYS_COMMON_PRIVATE("sys_common_private", "常规：公共私有列表"),
        SYS_YES_NO("sys_yes_no", "常规：是否列表"),
        SYS_USER_SEX("sys_user_sex", "用户性别列表");

        private final String code;
        private final String info;

        DictType(String code, String info) {
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
}
