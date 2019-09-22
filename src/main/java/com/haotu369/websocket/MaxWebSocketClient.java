package com.haotu369.websocket;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 抹茶Websocket客户端
 *
 * @author Jian Shen
 * @version V1.0.0
 * @date 2019/9/21
 */
@Component
public class MaxWebSocketClient implements ApplicationContextAware {

    @Value("${max.url}")
    private String maxUrl;

    @Value("${max.topic}")
    private String maxTopic;

    @Autowired
    private MaxClientEndpoint maxClientEndpoint;

    private MaxWebSocketClient() {}

    private void start() {
       WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            Session session = container.connectToServer(maxClientEndpoint, new URI(maxUrl));
            while (true) {
                session.getBasicRemote().sendText(maxTopic);
                Thread.sleep(5000);
            }
        } catch (DeploymentException | IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MaxWebSocketClient maxWebSocketClient = applicationContext.getBean("maxWebSocketClient", MaxWebSocketClient.class);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            maxWebSocketClient.start();
        });
    }
}
