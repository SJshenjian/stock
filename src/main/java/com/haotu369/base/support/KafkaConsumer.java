package com.haotu369.base.support;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Jian Shen
 * @version V1.0.0
 * @date 2019/9/21
 */
@Service
public class KafkaConsumer {

    @Autowired
    private MongoTemplate mongoTemplate;

    @KafkaListener(topics = {"${kafka.consumer.topic.price-data}"}, groupId = "${kafka.consumer.group.price-data}")
    public void consumer(String message) {
        if (message.startsWith("MAX-42[\"push.overview\",{")) {

            message = message.replace("MAX-42[\"push.overview\",", "{\"date\":\"" + new Date() + "\",\"priceData\": ");
            message = message.replace("]", "}");
            mongoTemplate.save(JSONObject.parseObject(message), "spider.priceData");
        }
    }
}
