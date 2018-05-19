package com.haotu369.base;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/19
 */
@Component
public class MessageResult {

    public JSONObject message(int code, String note) {
        JSONObject result = new JSONObject();
        result.put("O_CODE", code);
        result.put("O_NOTE", note);
        return result;
    }
}
