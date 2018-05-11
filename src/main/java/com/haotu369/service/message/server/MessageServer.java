package com.haotu369.service.message.server;

import com.haotu369.service.message.common.Constant;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

import java.io.IOException;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/11
 */
public class MessageServer {

    // handler, 包括编码、解码、消息处理
    public static ServerAioHandler serverAioHandler = new MessageServerAioHandler();

    // 事件监听器，可以为null,但建议自己实现该接口，可以参考showcase了解些接口
    public static ServerAioListener serverAioListener = null;

    // 一组连接公用的上下文对象
    public static ServerGroupContext serverGroupContext = new ServerGroupContext("message-tio-server", serverAioHandler, serverAioListener);

    public static AioServer aioServer =  new AioServer(serverGroupContext);

    // 有时候需要绑定ip, 不需要则为null
    public static String serverIp = null;

    // 监听的端口
    public static int serverPort = Constant.PORT;

    public static void start() throws IOException {
        serverGroupContext.setHeartbeatTimeout(Constant.TIMEOUT);
        aioServer.start(serverIp, serverPort);
    }
}
