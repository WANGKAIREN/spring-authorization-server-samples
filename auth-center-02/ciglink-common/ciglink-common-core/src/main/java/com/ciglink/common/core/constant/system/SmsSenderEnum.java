package com.ciglink.common.core.constant.system;

import com.ciglink.common.core.exception.ServiceException;

public enum SmsSenderEnum {

    /**
     * 通道类型：0-验证码类；1-通知类；
     */
    VALIDATE(0, "8821063028146", "validateSender -> 验证码类"),
    INFORM(1, "8821062128263", "informSender -> 通知类");

    private final Integer key;
    private final String sender;
    private final String desc;

    SmsSenderEnum(Integer key, String sender, String desc) {
        this.key = key;
        this.sender = sender;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getSender() {
        return sender;
    }

    public String getDesc() {
        return desc;
    }

    public static String getTypeByKey(Integer key) {
        for (SmsSenderEnum value : values()) {
            if(key != null && key.intValue() == value.key) {
                return value.getSender();
            }
        }

        throw new ServiceException(MessageCode.NO_SUCH_SENDER.getMessage());
    }

}
