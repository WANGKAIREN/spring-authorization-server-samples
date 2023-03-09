package com.ciglink.common.core.constant.system;

public class SmsConstants {

	public static final String SMSPREFIX = "COS:SMS:";// 短信缓存前缀

	public static final String HUAWEI_CLOUD_SMS_SIGNATURE = "致格优链";

    public static final String HUAWEI_CLOUD_SMS_URL = "https://rtcsms.cn-north-1.myhuaweicloud.com:10743/sms/batchSendSms/v1";

    public static final String HUAWEI_CLOUD_SMS_KEY = "X4p05Nn0TP7c2C0s3xS29950YA5Z";

    public static final String HUAWEI_CLOUD_SMS_SECRET = "ip08Y4EPO3v720Mr4PfBwEB6vnJL";

    public static final String HUAWEI_CLOUD_SMS_WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";

    public static final String HUAWEI_CLOUD_SMS_AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";
	
    /**
     * 各条短信之间发送间隔(s)
     */
    public static final String HUAWEI_CLOUD_SMS_SEND_SECONDS = "60";

    /**
     * 一天允许发送的最大条数(条)
     */
    public static final String HUAWEI_CLOUD_SMS_DAY_COUNT = "10";

    /**
     * 短信发送频率控制 0.允许发送 1.1min只允许发送一条 2.一天只能发送10条
     */
    public static final int HUAWEI_CLOUD_SMS_STATE_0 = 0;
    public static final int HUAWEI_CLOUD_SMS_STATE_1 = 1;
    public static final int HUAWEI_CLOUD_SMS_STATE_2 = 2;

}
