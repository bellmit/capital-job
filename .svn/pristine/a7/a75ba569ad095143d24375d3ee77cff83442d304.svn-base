package com.yixin.alixjob.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;


public class JsonUtils {
    public static  <T> T parseObject(String jsonText,Class<T> clazz){
        return JSON.parseObject(jsonText,clazz);
    }
    /**
     *
     * String jsonStr = "[{\"id\":1001,\"name\":\"Jobs\"}]";
     * @param jsonText
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String jsonText, TypeReference<T> type){
        return JSON.parseObject(jsonText,type,null);
    }
}
