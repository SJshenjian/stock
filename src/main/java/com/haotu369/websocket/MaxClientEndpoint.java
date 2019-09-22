package com.haotu369.websocket;

import com.haotu369.base.config.KafkaConstant;
import com.haotu369.base.support.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;

/**
 * 抹茶Websocket Endpoint
 *
 * @author Jian Shen
 * @version V1.0.0
 * @date 2019/9/21
 */
@Component
@ClientEndpoint
public class MaxClientEndpoint {

    @Autowired
    private KafkaProducer kafkaProducer;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: Max交易所");
    }

    /**
     * 接收服务器主动推动的消息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        kafkaProducer.sendMessage(KafkaConstant.Topic.PRICE_DATA_TOPIC.getValue(), "MAX-" + message);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Websocket closed: Max交易所");
    }
}
