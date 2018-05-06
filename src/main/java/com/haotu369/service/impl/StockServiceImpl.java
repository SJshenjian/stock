package com.haotu369.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.mapper.StockMapper;
import com.haotu369.model.stock.Stock;
import com.haotu369.model.stock.StockClassify;
import com.haotu369.model.stock.StockType;
import com.haotu369.service.StockService;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/4
 */
@Service
public class StockServiceImpl implements StockService {

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
}
