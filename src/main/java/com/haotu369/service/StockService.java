package com.haotu369.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.stock.Stock;
import com.haotu369.model.stock.StockClassify;
import com.haotu369.model.stock.StockType;

import java.io.IOException;
import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/4
 */
 public interface StockService {

     List<StockType> listStockType();

     List<StockClassify> listStockClassify(int type);

     List<Stock> listStock(String classifyName);

     List<Stock> listComponentStock(String componentName);

     JSONObject messageClient2Client(String message);

     JSONObject getStockHistory(String code) throws IOException;
}
