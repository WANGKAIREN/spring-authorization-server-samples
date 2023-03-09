package com.ciglink.common.core.constant.system;

/**
 * 组织通用常量
 */
public class OrganizeConstants {
	
	/** 默认策略源Id */
	public static final Long STRATEGIC_RESOURCES = 1L;
	
    /** 企业账号长度限制 */
    public static final int ENTERPRISE_NAME_MIN_LENGTH = 2;

    public static final int ENTERPRISE_NAME_MAX_LENGTH = 30;
    
    /** 企业全称长度限制 */
    public static final int ENTERPRISE_NICK_MIN_LENGTH = 1;
    
    public static final int ENTERPRISE_NICK_MAX_LENGTH = 50;

    /** 用户名长度限制 */
    public static final int USERNAME_MIN_LENGTH = 2;

    public static final int USERNAME_MAX_LENGTH = 20;

    /** 密码长度限制 */
    public static final int PASSWORD_MIN_LENGTH = 5;

    public static final int PASSWORD_MAX_LENGTH = 20;
    
    /** 手机号码长度限制 */
    public static final int PHONE_MIN_LENGTH = 0;
    
    public static final int PHONE_MAX_LENGTH = 12;

    /** 组织树类型 */
    public enum OrganizeType {

        ENTERPRISE("0", "企业级"),
        DEPT("1", "部门级"),
        POST("2", "岗位级"),
        USER("3", "用户级");

        private final String code;
        private final String info;

        OrganizeType(String code, String info) {
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