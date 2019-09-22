package com.haotu369.base.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author Jian Shen
 * @version V1.0.0
 * @date 2019/8/3
 */
@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String data) {
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.error("Kafka send message error = {}, topic = {}, data = {}",
                        throwable, topic, data);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> integerStringSendResult) {

            }
        });
    }
}
