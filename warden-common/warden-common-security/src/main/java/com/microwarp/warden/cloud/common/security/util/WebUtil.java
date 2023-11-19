package com.microwarp.warden.cloud.common.security.util;

import com.microwarp.warden.cloud.common.core.constant.HttpConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * web - util
 * @author zhouwenqi
 */
public class WebUtil {
    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
    private final static String UNKNOWN = "unknown";

    public static HttpServletRequest getRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    public static HttpServletResponse getResponse(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) requestAttributes).getResponse();
    }

    /**
     * 获取 body 数据
     * @param request
     * @return body 字符串内容
     */
    public static String getBody(HttpServletRequest request){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine())!= null){
                stringBuilder.append(line);
            }
            reader.close();
        }catch (IOException e){
            logger.warn("获取request-body失败:{}",e.getMessage());
        }
        return stringBuilder.toString();
    }

    /**
     * 获取当前客户端IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        Assert.notNull(request, "HttpServletRequest is null");
        String ip = request.getHeader("X-Requested-For");
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return StringUtils.isBlank(ip) ? null : ip.split(",")[0];
    }

    /**
     * 获取当前客户端IP地址
     * @return
     */
    public static String getIpAddr(){
        return getIpAddr(getRequest());
    }

    /**
     * 获取当前guestId
     * @return
     */
    public static String getGuestId(){
        HttpServletRequest request = getRequest();
        Object guestObject = request.getAttribute(HttpConstant.HEADER_GUEST_KEY);

        return null == guestObject ? null : guestObject.toString();
    }
}
