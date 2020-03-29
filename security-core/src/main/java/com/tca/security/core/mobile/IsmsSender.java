package com.tca.security.core.mobile;

/**
 * @author zhoua
 * @Date 2020/3/27
 * 短信发送
 */
public interface IsmsSender {

    /**
     * 发送短信
     * @param mobile
     * @param code
     * @return
     */
    boolean sendValidateCode(String mobile, String code);
}
