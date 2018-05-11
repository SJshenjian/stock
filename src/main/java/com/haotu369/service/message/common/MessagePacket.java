package com.haotu369.service.message.common;

import org.tio.core.intf.Packet;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/11
 */
public class MessagePacket extends Packet {
    private byte[] body;

    /**
     * 响应头的长度
     */
    public static final int HEADER_LENGTH = 4;

    /**
     * 编码
     */
    public static final String CHARSET = "utf-8";

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
