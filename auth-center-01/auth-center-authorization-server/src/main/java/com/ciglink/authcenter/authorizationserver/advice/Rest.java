package com.ciglink.authcenter.authorizationserver.advice;

/**
 *
 * @param <T> the type parameter
 * @author WANGKairen
 * @since 2022-12-29 13:58:05
 **/
public interface Rest<T> {

    /**
     * 业务状态码，设计时应该区别于http状态码.
     *
     * @param code the code
     */
    void setCode(int code);

    /**
     * 数据载体.
     *
     * @param data the data
     */
    void setData(T data);

    /**
     * 提示信息.
     *
     * @param msg the msg
     */
    void setMsg(String msg);

    /**
     * 预留的标识位，作为一些业务的处理标识位.
     *
     * @param identifier 标识
     */
    void setIdentifier(boolean identifier);
}
