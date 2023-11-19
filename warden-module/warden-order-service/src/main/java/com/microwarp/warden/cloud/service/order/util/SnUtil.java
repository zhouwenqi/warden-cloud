package com.microwarp.warden.cloud.service.order.util;

import com.microwarp.warden.cloud.common.core.handler.SnowFlake;
import com.microwarp.warden.cloud.facade.order.enums.SnEnum;

/**
 * sn - util
 */
public class SnUtil {
    /**
     * 雪花算法随机生成SN
     * @return SN
     */
    public static String generateSN(SnEnum snEnum){
        SnowFlake snowFlake = new SnowFlake(1,1,1);
        return snEnum.name() + snowFlake.nextId();
    }
}
