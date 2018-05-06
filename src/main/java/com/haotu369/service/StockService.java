package com.haotu369.service;

import com.haotu369.model.stock.Stock;
import com.haotu369.model.stock.StockClassify;
import com.haotu369.model.stock.StockType;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/4
 */
public interface StockService {

    public List<StockType> listStockType();

    public List<StockClassify> listStockClassify(int type);

    public List<Stock> listStock(String classifyName);

    public List<Stock> listComponentStock(String componentName);
}
