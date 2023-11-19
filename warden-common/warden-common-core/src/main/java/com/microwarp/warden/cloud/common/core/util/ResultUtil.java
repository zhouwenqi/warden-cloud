package com.microwarp.warden.cloud.common.core.util;

import com.microwarp.warden.cloud.common.core.enums.PlatformTypeEnum;
import com.microwarp.warden.cloud.common.core.model.ResultCode;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.security.UserType;

import java.util.HashMap;
import java.util.Map;

/**
 * result - util
 * @author zhouwenqi
 */
public class ResultUtil {
    /**
     * ResultModel转换Map
     * @param resultModel
     * @return
     */
    public static Map<String,Object> getEntityMap(ResultModel resultModel){
        Map<String,Object> map = new HashMap<>();
        map.put("code",resultModel.getCode());
        map.put("msg",resultModel.getMsg());
        map.put("data",resultModel.getData());
        return map;
    }
    /**
     * TokenUser用户类型转平台类型
     * @param userType 用户类型
     * @return
     */
    public static PlatformTypeEnum convertToPlatform(UserType userType){
        PlatformTypeEnum platformTypeEnum = PlatformTypeEnum.UNKNOWN;
        switch (userType){
            case SYSTEM:
                platformTypeEnum = PlatformTypeEnum.BACKSTAGE;
                break;
            case NORMAL:
                platformTypeEnum = PlatformTypeEnum.FORESTAGE;
                break;
            case GUEST:
                platformTypeEnum = PlatformTypeEnum.UNKNOWN;
        }
        return platformTypeEnum;
    }

    /**
     * 检查状态码是否为warden包装类型;
     * @param status 状态码
     * @return
     */
    public static boolean isResultCode(int status){
        return null != ResultCode.resolve(status);
    }

}
