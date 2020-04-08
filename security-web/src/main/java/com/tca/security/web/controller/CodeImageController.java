package com.tca.security.web.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.tca.security.core.constant.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author zhoua
 * @Date 2020/3/26
 * 验证码
 */
@Controller
@Slf4j
public class CodeImageController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @RequestMapping("/code/image")
    public void codeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.生成验证码
        String code = defaultKaptcha.createText();
        log.info("生成验证码: {}", code);
        BufferedImage image = defaultKaptcha.createImage(code);
        // 2.验证码放入session
        request.getSession().setAttribute(SecurityConstants.CODE_IMAGE_SESSION_ID, code);
        // 3.将图形验证码返回给前端(流的方式)
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
    }
}
