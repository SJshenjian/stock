package com.haotu369.service.message;

import com.haotu369.service.message.client.MessageClient;
import com.haotu369.service.message.server.MessageServer;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/11
 */
@Component
public class Message {

    public Message() throws Exception {
        //start();
    }

    public static void start() throws Exception {
        MessageServer.start();
        MessageClient.start();
    }
}
