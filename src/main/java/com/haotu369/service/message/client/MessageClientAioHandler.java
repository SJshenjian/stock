package com.haotu369.service.message.client;

import com.haotu369.service.message.common.MessagePacket;
import com.haotu369.service.message.server.MessageServerAioHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/11
 */
public class MessageClientAioHandler implements ClientAioHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageClientAioHandler.class);

    private static MessagePacket messagePacket = new MessagePacket();

    public static volatile String responseMessage = null;

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
        if (readableLength < MessagePacket.HEADER_LENGTH) {
            return null;
        }

        int bodyLength = byteBuffer.getInt();
        if (bodyLength < 0) {
            throw new AioDecodeException("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getServerNode());
        }

        int neededLength = MessagePacket.HEADER_LENGTH + bodyLength;
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
     * 消息编码
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

        byteBuffer.order(groupContext.getByteOrder());
        byteBuffer.putInt(bodyLength);

        if (body != null) {
            byteBuffer.put(body);
        }
        return byteBuffer;
    }

    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
        MessagePacket messagePacket = (MessagePacket) packet;
        byte[] body = messagePacket.getBody();
        if (body != null) {
            responseMessage = new String(body, MessagePacket.CHARSET);
            LOGGER.info("客户端收到消息: {} ", responseMessage);
        }
    }

    /**
     * 此方法如果返回null，框架层面则不会发心跳；如果返回非null，框架层面会定时发本方法返回的消息包
     */
    @Override
    public MessagePacket heartbeatPacket() {
        return messagePacket;
    }

}
