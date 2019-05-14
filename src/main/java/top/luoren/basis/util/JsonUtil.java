package top.luoren.basis.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author luoren
 * @date 2019-05-14 16:59
 */
public class JsonUtil {
    /**
     * 将string转化为序列化的json字符串
     */
    public static Object textToJson(String text) {
        Object objectJson = JSON.parse(text);
        return objectJson;
    }

    /**
     * json字符串转化为map
     */
    public static Map stringToCollect(String s) {
        Map m = JSONObject.parseObject(s);
        return m;
    }

    /**
     * 将map转化为string
     */
    public static String collectToString(Map m) {
        String s = JSONObject.toJSONString(m);
        return s;
    }

    /**
     * 返回解析json后对应的Map
     */
    public static Map<String, Object> jsonToMap(String json) {
        JSONObject jsonObject = (JSONObject) JSON.parse(json);
        Map<String, Object> map = Maps.newHashMap(jsonObject);
        return map;
    }


    /**
     * 返回解析json后对应的Object
     */
    public static JSONObject jsonToObject(String json) {
        JSONObject jsonObject = (JSONObject) JSON.parse(json);
        return jsonObject;
    }
}
