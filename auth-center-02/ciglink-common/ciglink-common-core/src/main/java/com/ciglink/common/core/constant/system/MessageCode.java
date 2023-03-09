package com.ciglink.common.core.constant.system;


/**
 * 状态码(请尽可能使用该枚举，保证响应给用户的信息规范且友好)
 * 200       请求成功
 * 400x      请求错误
 * 500x      系统错误
 * 900x      其他错误（一般不对系统外开放该类型异常）
 *
 * @author xuye
 * @version 2021.04.14
 * @since 1.8
 */
public enum MessageCode {
    SMS_SEND_FAILURE(5006, "短信发送失败，请联系系统管理员"),
    NO_SUCH_TEMPLATE(9001, "未找到华为云短信: template"),
    NO_SUCH_SENDER(9002, "未找到华为云短信: sender");

    private final Integer code;

    private final String message;

    MessageCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static MessageCode getMessageCode(Integer code) {
        for (MessageCode item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
