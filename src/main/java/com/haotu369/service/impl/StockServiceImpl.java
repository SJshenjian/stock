package com.haotu369.service.impl;

import com.haotu369.mapper.StockMapper;
import com.haotu369.model.stock.StockClassify;
import com.haotu369.model.stock.StockType;
import com.haotu369.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
