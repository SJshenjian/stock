package com.haotu369.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haotu369.mapper.StockMapper;
import com.haotu369.model.stock.Stock;
import com.haotu369.model.stock.StockClassify;
import com.haotu369.model.stock.StockType;
import com.haotu369.service.StockService;
import com.haotu369.service.message.client.MessageClient;
import com.haotu369.service.message.common.MessagePacket;
import com.haotu369.service.message.server.MessageServer;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.tio.client.AioClient;
import org.tio.core.Aio;
import org.tio.server.ServerChannelContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/4
 */
@Service
public class StockServiceImpl implements StockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private StockMapper stockMapper;

    @Override
    public List<StockType> listStockType() {
        return stockMapper.listStockType();
    }

    @Override
    public List<StockClassify> listStockClassify(int type) {
        return stockMapper.listStockClassify(type);
    }

    @Override
    public List<Stock> listStock(String classifyName) {
        return stockMapper.listStock(classifyName);
    }

    @Override
    public List<Stock> listComponentStock(String componentName) {
        if ("sz50".equalsIgnoreCase(componentName)) {
            return stockMapper.listSz50Stock();
        }

        if ("zz500".equalsIgnoreCase(componentName)) {
            return stockMapper.listZz500Stock();
        }

        if ("hs300".equalsIgnoreCase(componentName)) {
            return stockMapper.listHs300Stock();
        }

        return null;
    }

    @Override
    public JSONObject messageClient2Client(String message) {
        if (StringUtils.isEmpty(message)) {
            return message(-1, "消息不能为空", null);
        }

        try {
            // 客户端消息发送
            MessagePacket clientMessagePacket = new MessagePacket();
            clientMessagePacket.setBody(message.getBytes(MessagePacket.CHARSET));
            Aio.send(MessageClient.clientChannelContext, clientMessagePacket);

            while (MessageClient.getServerResponseMessage() == null) {

            }

            JSONObject result = JSONObject.parseObject(MessageClient.getServerResponseMessage());

            MessageClient.setServerResponseMessageNull();
            if (result != null) {
                return message(1, "成功", result);
            }
        } catch (UnsupportedEncodingException e) {
           LOGGER.error("消息编码异常：{}", e.getMessage());
        }
        return message(-1, "服务器响应失败", null);

    }

    private JSONObject message(int code, String note, JSONObject result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("O_CODE", code);
        jsonObject.put("O_NOTE", note);
        jsonObject.put("O_RESULT", result);
        return jsonObject;
    }
}
