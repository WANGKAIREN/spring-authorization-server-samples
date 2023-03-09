package com.ciglink.common.core.exception;

/**
 * @Description : bean属性值验证失败异常
 */
public class BeanValidateException extends RuntimeException{


    private static final long serialVersionUID = 1L;

    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 错误信息
     */
    private String message;

    public BeanValidateException(){
    }
    public BeanValidateException(String message){
        this.message = message;
    }
    public BeanValidateException(String fieldName, String message){
        this.fieldName=fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
