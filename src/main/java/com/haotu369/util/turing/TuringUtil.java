package com.haotu369.util.turing;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.haotu369.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;


/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/12
 */
public class TuringUtil {

    public static final String turingApi = "http://openapi.tuling123.com/openapi/api/v2";

    public static String turingKey = "cb9122b249b74b61b9827ffa1aa02efc";

    /**
     * 与图灵机器人交流
     *
     * @param username
     * @param message
     * @return
     */
    public static String chat(String username, String message) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(message)) {
            return null;
        }

        String paramsString = "{" +
                "\"reqType\":0," +
                "    \"perception\": {" +
                "        \"inputText\": {" +
                "            \"text\": \"" + message + "\"" +
                "        }" +
                "    }," +
                "    \"userInfo\": {" +
                "        \"apiKey\": \"cb9122b249b74b61b9827ffa1aa02efc\"," +
                "        \"userId\": \"turing\"" +
                "    }" +
                "}";
        JSONObject params = JSONObject.parseObject(paramsString);
        String responseContent = HttpUtil.doPost(turingApi, params);

        return responseContent;
    }
}
