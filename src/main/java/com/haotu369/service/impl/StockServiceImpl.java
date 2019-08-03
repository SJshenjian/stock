package com.haotu369.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.base.ContextPath;
import com.haotu369.base.Result;
import com.haotu369.mapper.StockMapper;
import com.haotu369.model.stock.Stock;
import com.haotu369.model.stock.StockClassify;
import com.haotu369.model.stock.StockType;
import com.haotu369.service.StockService;
import com.haotu369.service.message.client.MessageClient;
import com.haotu369.service.message.common.MessagePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;;
import org.tio.core.Aio;

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
@Transactional
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
            return Result.jsonResult(-1, "消息不能为空", null);
        }

        try {
            // 客户端消息发送
            MessagePacket clientMessagePacket = new MessagePacket();
            clientMessagePacket.setBody(message.getBytes(MessagePacket.CHARSET));
            Aio.send(MessageClient.clientChannelContext, clientMessagePacket);

            JSONObject result = JSONObject.parseObject(MessageClient.getServerResponseMessage());

            if (result != null) {
                return Result.jsonResult(1, "成功", result);
            }
        } catch (UnsupportedEncodingException e) {
           LOGGER.error("消息编码异常：{}", e.getMessage());
        }
        return Result.jsonResult(-1, "服务器响应失败", null);

    }

    @Override
    public JSONObject getStockHistory(String code) throws IOException {
        String path = ContextPath.getContextPath("script/StockHistory.py");
        String command = "python " + path + " " + code;
        Process process = Runtime.getRuntime().exec(command);

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String content = null;
        while ((content = reader.readLine()) != null) {
            sb.append(content);
        }

        JSONObject result = JSONObject.parseObject(sb.toString());
        LOGGER.debug("获取股票 {}, 历史数据为 {}", code, result);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("O_CODE", 1);
        jsonObject.put("O_NOTE", "成功");
        jsonObject.put("O_RESULT", result);

        return jsonObject;
    }
}
