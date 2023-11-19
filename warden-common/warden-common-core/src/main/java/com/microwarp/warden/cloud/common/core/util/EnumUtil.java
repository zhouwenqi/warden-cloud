package com.microwarp.warden.cloud.common.core.util;


import com.microwarp.warden.cloud.common.core.enums.BaseEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * enum - util
 */
public class EnumUtil {
    public static List<Integer> getCodes(BaseEnum[] baseEnum){
        List<Integer> list = new ArrayList<>();
        if(null == baseEnum || baseEnum.length <0){
            return list;
        }
        for(BaseEnum e:baseEnum){
            list.add(e.getCode());
        }
        return list;
    }
}
