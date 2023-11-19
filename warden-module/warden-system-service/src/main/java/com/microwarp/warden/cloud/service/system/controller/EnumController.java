package com.microwarp.warden.cloud.service.system.controller;

import com.microwarp.warden.cloud.common.core.domain.pojo.DictionaryItem;
import com.microwarp.warden.cloud.common.core.domain.pojo.WardenDictionary;
import com.microwarp.warden.cloud.common.core.exception.WardenRequireParamterException;
import com.microwarp.warden.cloud.common.core.model.ResultModel;
import com.microwarp.warden.cloud.common.core.util.DictUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controller - 枚举字典数据
 * @author zhouwenqi
 */
@RequestMapping("/enum")
@RestController
public class EnumController {
    /**
     * 获取枚举字典列表
     * @param keys 枚举名称列表
     * @return
     */
    @GetMapping("dictionarys/{keys}")
    public ResultModel enums(@PathVariable String[] keys){
        if(null == keys || keys.length < 1){
            throw new WardenRequireParamterException("Key(枚举类名)不为为空");
        }
        Map<String,Object> map = new HashMap<>();
        for(String key:keys){
            WardenDictionary wardenDictionary = DictUtil.getEnumDictionary(key);
            List<DictionaryItem> list = null != wardenDictionary ? wardenDictionary.getItems() : new ArrayList<>();
            map.put(key, list);
        }
        ResultModel resultModel = ResultModel.success();
        resultModel.addData(map);
        return resultModel;
    }
}
