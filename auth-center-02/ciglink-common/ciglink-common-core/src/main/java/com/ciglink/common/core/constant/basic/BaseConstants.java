package com.ciglink.common.core.constant.basic;

import cn.hutool.core.util.StrUtil;

/**
 * 通用常量
 */
public class BaseConstants {
	
	/** 删除0-正常 */
	public static final Long DEL_FLAG_NORMAL = 0L;
	
	/** 删除1-已删除 */
	public static final Long DEL_FLAG_DELETED = 1L;

    /**
     * 顶级节点Id
     */
    public static final Long TOP_ID = 0L;

    /**
     * 公共节点Id
     */
    public static final Long COMMON_ID = 0L;

    /**
     * 空节点Id
     */
    public static final Long NONE_ID = -2L;

    /**
     * 默认 - 非租户表配置
     */
    public static final String[] INIT_EXCLUDE_TABLE = {"sys_config", "sys_dict_type", "sys_dict_data", "te_strategy"};

    /**
     * 默认 - 公共表配置
     */
    public static final String[] INIT_COMMON_TABLE = {};

    /**
     * 操作类型
     */
    public enum Operate {

        ADD("add", "新增"),
        ADD_FORCE("add", "强制新增"),
        EDIT("edit", "修改"),
        EDIT_FORCE("edit", "强制修改"),
        EDIT_STATUS("editStatus", "修改状态"),
        EDIT_STATUS_FORCE("editStatus", "强制修改状态"),
        DELETE("delete", "删除"),
        DELETE_FORCE("delete", "强制删除");

        private final String code;
        private final String info;

        Operate(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        /**
         * 是否为新增 | 强制新增
         */
        public boolean isAdd() {
            return StrUtil.equalsAny(name(), Operate.ADD.name(), Operate.ADD_FORCE.name());
        }

        /**
         * 是否为修改 | 强制修改
         */
        public boolean isEdit() {
            return StrUtil.equalsAny(name(), Operate.EDIT.name(), Operate.EDIT_FORCE.name());
        }

        /**
         * 是否为修改状态
         */
        public boolean isES() {
            return StrUtil.equals(name(), Operate.EDIT_STATUS.name());
        }

        /**
         * 是否为强制修改状态
         */
        public boolean isESForce() {
            return StrUtil.equals(name(), Operate.EDIT_STATUS_FORCE.name());
        }

        /**
         * 是否为删除
         */
        public boolean isDelete() {
            return StrUtil.equals(name(), Operate.DELETE.name());
        }

        /**
         * 是否为强制删除
         */
        public boolean isDelForce() {
            return StrUtil.equals(name(), Operate.DELETE_FORCE.name());
        }
    }

    /**
     * 字段映射名
     */
    public enum Entity {

        ID("id", "Id"),
        PARENT_ID("parentId", "父级Id"),
        SORT("sort", "显示顺序"),
        CHILDREN("children", "子部门集合");

        private final String code;
        private final String info;

        Entity(String code, String info) {
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

    /**
     * 状态
     */
    public enum Status {

        NORMAL("0", "正常"),
        DISABLE("1", "停用"),
        EXCEPTION("2", "异常");

        private final String code;
        private final String info;

        Status(String code, String info) {
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

    public enum CommonBoolean {

        /** 所有 */
        all(-1, "所有"),
        /** 未知 */
        FALSE(0, "否"),
        /** 正常 */
        TRUE(1, "是");
        private final int code;
        private final String info;

        CommonBoolean(int code, String info) {
            this.code = code;
            this.info = info;
        }

        public int getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static CommonBoolean getByCode(Integer code) {
            if (code == null) {
                return CommonBoolean.all;
            }
            for (CommonBoolean e : CommonBoolean.values()) {
                if (code.equals(e.getCode())) {
                    return e;
                }
            }
            return CommonBoolean.FALSE;
        }

        public static boolean getBooleanByCode(Integer code) {
            return code == CommonBoolean.TRUE.code;
        }

        public static int toInt(boolean x) {
            return Boolean.compare(x, false);
        }
    }

    /**
     * 系统默认值
     */
    public enum Whether {

        YES("Y", "是"),
        NO("N", "否");

        private final String code;
        private final String info;

        Whether(String code, String info) {
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
