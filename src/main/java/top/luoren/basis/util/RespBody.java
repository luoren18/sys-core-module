package top.luoren.basis.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoren
 * @date 2019-04-28 11:27
 */
public class RespBody extends HashMap<String, Object> {
    public static final String OK = "0";
    public static final String ERROR = "-1";

    public static RespBody error() {
        return error(ERROR, "未知异常，请联系管理员");
    }

    public static RespBody error(String msg) {
        return error(ERROR, msg);
    }

    public static RespBody error(String code, String msg) {
        RespBody r = new RespBody();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static RespBody ok(String msg) {
        RespBody r = new RespBody();
        r.put("code", OK);
        r.put("msg", msg);
        return r;
    }

    public static RespBody ok(Map<String, Object> map) {
        RespBody r = ok();
        r.putAll(map);
        return r;
    }

    public static RespBody ok() {
        RespBody r = new RespBody();
        r.put("code", OK);
        r.put("msg", "SUCCESS");
        return r;
    }

    @Override
    public RespBody put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
