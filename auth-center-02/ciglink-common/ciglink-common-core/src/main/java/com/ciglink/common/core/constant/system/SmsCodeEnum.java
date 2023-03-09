package com.ciglink.common.core.constant.system;

import com.ciglink.common.core.exception.ServiceException;

public enum SmsCodeEnum {

    /**
     * 发送验证码类型：0-手机验证码登录；1-忘记密码；2-修改手机号；3-预估额度；4-注册；5-修改密码；6-更新物流商信息
     */
    PHONECODE(0, "d0d1ab50688c462794da7ef21d6c49a9", "phoneLoginTemplateId -> 手机验证码登录"),
    FORGETCODE(1, "36168f039b264bb3a0c8a67be30fff7f", "forgetPasswordTemplateId -> 忘记密码"),
    UPDATEPHONE(2, "b61c562c823945e3a8a2eb2b34381c91", "updatePhoneTemplateId -> 修改手机号"),
    ESTIMATE(3, "cab6b1cc49f549ceb4f85bc1500b14e9", "estimateTemplateId -> 额度预估通知"),
    REG(4, "a6b7f09cdcb649bb924d072d7672f47c", "regTemplateId -> 注册"),
    UPDATEPASSWORD(5, "d4174bdcec14425e932366a630d297a0", "updatePasswordTemplateId -> 修改密码");

    private final Integer key;
    private final String templateId;
    private final String desc;

    SmsCodeEnum(Integer key, String templateId, String desc) {
        this.key = key;
        this.templateId = templateId;
        this.desc = desc;
    }

    public Integer getKey() {
        return key;
    }

    public String getTemplateId() {
        return templateId;
    }

    public String getDesc() {
        return desc;
    }

    public static String getTemplateIdByKey(Integer key) {
        for (SmsCodeEnum value : values()) {
            if(key != null && key.intValue() == value.key) {
                return value.getTemplateId();
            }
        }

        throw new ServiceException(MessageCode.NO_SUCH_TEMPLATE.getMessage());
    }

}
