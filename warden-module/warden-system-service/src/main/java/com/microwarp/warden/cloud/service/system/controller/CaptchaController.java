package com.microwarp.warden.cloud.service.system.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireParamterException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.util.AesUtil;
import com.microwarp.warden.cloud.common.security.service.CaptchaService;
import org.apache.commons.crypto.utils.IoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * controller - 图片验证码
 * @author zhouwenqi
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private CaptchaService captchaService;

    /**
     * 获取Base64验证码
     * @return
     * @throws IOException
     */
    @GetMapping("base64")
    @ResponseBody
    public ResultModel base64() throws IOException{
        ResultModel resultModel = ResultModel.success();
        String code = defaultKaptcha.createText();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = defaultKaptcha.createImage(code);
        ImageIO.write(bufferedImage,"jpg",outputStream);
        resultModel.addData("imgData", Base64.getEncoder().encodeToString(outputStream.toByteArray()));
        // 验证码存入缓存
        captchaService.save(code);
        return resultModel;
    }

    /**
     * 获取图片验证码图片流
     * @param response
     * @throws IOException
     */
    @GetMapping("image/{guestId}")
    public void image(@PathVariable("guestId") String guestId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control","no-store");
        response.setContentType("image/jpeg");
        try {
            AesUtil.hexDecrypt(guestId);
            request.setAttribute(HttpConstant.HEADER_GUEST_KEY,guestId);
        }
        catch (Exception e){
            logger.warn("guestId校验失败");
            throw new WardenRequireParamterException("缺少Guest-Id参数");
        }

        String code = defaultKaptcha.createText();
        // 验证码存入缓存
        captchaService.save(code);
        BufferedImage bufferedImage = defaultKaptcha.createImage(code);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage,"jpg",outputStream);
        IoUtils.closeQuietly(outputStream);
    }
}
