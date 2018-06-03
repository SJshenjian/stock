package com.haotu369.service.message.client;

import com.haotu369.service.message.common.Constant;
import com.haotu369.service.message.common.MessagePacket;
import com.haotu369.service.message.server.MessageServerAioHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.Node;

import java.io.UnsupportedEncodingException;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/11
 */
public class MessageClient {
    public static Node serverNode = new Node(Constant.SERVER, Constant.PORT);

    // handle, 包括编码、解码、消息处理
    public static ClientAioHandler clientAioHandler = new MessageClientAioHandler();

    public static ClientAioListener clientAioListener = null;

    // 断链重连用，不重连则设置为null
    public static ReconnConf reconnConf = new ReconnConf(5000L);

    // 一组连接公用的上下文对象
    public static ClientGroupContext clientGroupContext = new ClientGroupContext(clientAioHandler, clientAioListener, reconnConf);

    public static AioClient aioClient = null;

    public static ClientChannelContext clientChannelContext = null;

    /**
     * 启动客户端程序
     *
     * @throws Exception
     */
    public static void start() throws Exception {
        clientGroupContext.setHeartbeatTimeout(Constant.TIMEOUT);
        aioClient = new AioClient(clientGroupContext);
        clientChannelContext = aioClient.connect(serverNode);
        //send();
    }

    /**
     * 获取服务端响应信息
     *
     * @return
     */
    public static String getServerResponseMessage() {
        return MessageClientAioHandler.getResponseMessage();
    }

    private static void send() throws UnsupportedEncodingException {
        MessagePacket messagePacket = new MessagePacket();
        messagePacket.setBody("客户端开始连接t-io,收到请回复".getBytes(MessagePacket.CHARSET));
        Aio.send(clientChannelContext, messagePacket);
    }
}
