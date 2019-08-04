package com.haotu369.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/12
 */
public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * POST请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, JSONObject params) {
        if (StringUtils.isEmpty(params)) {
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        System.out.println(params.toString());
        StringEntity stringEntity = new StringEntity(params.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);


        try (
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                // 执行请求
                CloseableHttpResponse response = httpClient.execute(httpPost);
        ){
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                return content;
            }

        } catch (IOException e) {
            LOGGER.error("执行请求 {} 失败", url);
        }
        return null;
    }
}
