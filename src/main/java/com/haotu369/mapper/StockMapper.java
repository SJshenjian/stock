package com.haotu369.mapper;

import com.haotu369.model.stock.Stock;
import com.haotu369.model.stock.StockClassify;
import com.haotu369.model.stock.StockType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/4
 */
@Repository
public interface StockMapper {

    @Select("SELECT * FROM stock_type")
    List<StockType> listStockType();

    @Select("SELECT * FROM stock_classify WHERE type = #{type}")
    List<StockClassify> listStockClassify(int type);

    @Select("SELECT * FROM stock WHERE c_name = #{classifyName}")
    List<Stock> listStock(String classifyName);

    @Select({"SELECT code, name FROM hs300_classify"})
    List<Stock> listHs300Stock();

    @Select({"SELECT code, name FROM zz500_classify"})
    List<Stock> listZz500Stock();

    @Select({"SELECT code, name FROM sz50_classify"})
    List<Stock> listSz50Stock();
}
