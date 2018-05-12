package com.haotu369.service.message.server;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.service.message.common.MessagePacket;
import com.haotu369.util.turing.TuringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

import java.nio.ByteBuffer;

/**
 * 消息服务端
 *
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/11
 */
public class MessageServerAioHandler implements ServerAioHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServerAioHandler.class);

    /**
     * 消息解码：把接收到的ByteBuffer,解码成应用可以识别的业务消息包
     *
     * @param byteBuffer
     * @param limit
     * @param position
     * @param readableLength
     * @param channelContext
     * @return
     * @throws AioDecodeException
     */
    @Override
    public Packet decode(ByteBuffer byteBuffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException {
        // 提醒： buffer的开始位置并不一定是0，应用需要从buffer.position()开始读取
        // 收到的数据组不了业务包，则返回null以告诉框架数据不够
        if (readableLength < MessagePacket.HEADER_LENGTH) {
            return null;
        }

        // 读取消息体的长度
        int bodyLength = byteBuffer.getInt();
        if (bodyLength < 0) {
            throw new AioDecodeException("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getServerNode());
        }

        // 计算本次需要的数据长度
        int neededLength = MessagePacket.HEADER_LENGTH + bodyLength;
        // 收到的数据是否足够组包
        int isDataEnough = readableLength - neededLength;

        if (isDataEnough < 0) {
            return null;
        }

        MessagePacket messagePacket = new MessagePacket();
        if (bodyLength > 0) {
            byte[] bytes = new byte[bodyLength];
            byteBuffer.get(bytes);
            messagePacket.setBody(bytes);
        }

        return messagePacket;
    }

    /**
     * 消息编码：把业务消息包编码为可以发送的ByteBuffer
     *
     * @param packet
     * @param groupContext
     * @param channelContext
     * @return
     */
    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
        MessagePacket messagePacket = (MessagePacket) packet;
        byte[] body = messagePacket.getBody();
        int bodyLength = 0;

        if (body != null) {
            bodyLength = body.length;
        }

        int allLength = MessagePacket.HEADER_LENGTH + bodyLength;
        ByteBuffer byteBuffer = ByteBuffer.allocate(allLength);

        // 设置字节序
        byteBuffer.order(groupContext.getByteOrder());
        //写入消息头----消息头的内容就是消息体的长度
        byteBuffer.putInt(bodyLength);

        if (body != null) {
            byteBuffer.put(body);
        }

        return byteBuffer;
    }

    /**
     * 消息处理
     *
     * @param packet
     * @param channelContext
     * @throws Exception
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        MessagePacket messagePacket = (MessagePacket) packet;
        byte[] body = messagePacket.getBody();
        if (body != null) {
            // 接收到的消息
            String receivedContent = new String(body, MessagePacket.CHARSET);
            LOGGER.info("服务端收到消息：{}", receivedContent);

            // 发送消息
            MessagePacket responseMessagePacket = new MessagePacket();

            String responseContent = TuringUtil.chat("客服", receivedContent);
            LOGGER.info("服务端发送消息：" + responseContent.toString());

            responseMessagePacket.setBody(responseContent.getBytes());

            Aio.send(channelContext, responseMessagePacket);
        }
    }
}
