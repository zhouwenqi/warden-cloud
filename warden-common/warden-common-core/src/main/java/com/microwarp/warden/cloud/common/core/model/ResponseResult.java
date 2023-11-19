package com.microwarp.warden.cloud.common.core.model;

import com.microwarp.warden.cloud.common.core.util.JsonUtil;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * warden统一输出数据模型
 * @author zhouwenqi
 */
public class ResponseResult {
    public static void output(ResultModel resultModel, HttpServletResponse response){
//        response.resetBuffer();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
//        response.setHeader("Access-Control-Allow-Origin","*");
//        response.setHeader("Access-Control-Allow-Headers","*");
//        response.setHeader("Access-Control-Allow-Methods","*");
//        response.setHeader("Access-Control-Allow-Credentials","true");
        String data = JsonUtil.objectToJson(resultModel);
        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(data.getBytes("utf-8"));
            outputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void print(ResultModel resultModel, HttpServletResponse response){
//        response.resetBuffer();
//        response.setContentType("application/json");
//        response.setCharacterEncoding("utf-8");
//        response.setHeader("Access-Control-Allow-Origin","*");
//        response.setHeader("Access-Control-Allow-Headers","*");
//        response.setHeader("Access-Control-Allow-Methods","*");
//        response.setHeader("Access-Control-Allow-Credentials","true");
        String data = JsonUtil.objectToJson(resultModel);
        try {
            response.getWriter().println(data);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
