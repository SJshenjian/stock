package com.haotu369.base;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/19
 */
public class Result {

    public static JSONObject message(int code, String note) {
        JSONObject result = new JSONObject();
        result.put("O_CODE", code);
        result.put("O_NOTE", note);
        return result;
    }

    public static JSONObject jsonResult(int code, String note, Object content) {
        JSONObject result = new JSONObject();
        result.put("O_CODE", code);
        result.put("O_NOTE", note);
        result.put("O_RESULT", content);
        return result;
    }
}
