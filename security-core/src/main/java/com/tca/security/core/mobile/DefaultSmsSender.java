package com.tca.security.core.mobile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhoua
 * @Date 2020/3/27
 */
@Component
@Slf4j
public class DefaultSmsSender implements IsmsSender {

    @Override
    public boolean sendValidateCode(String mobile, String code) {
        String message = String.format("登录验证码为%s, 请勿泄露!", code);
        log.info("手机号: {}, 短信内容: {}", mobile, message);
        return true;
    }
}
