package com.haotu369.model.stock;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/5
 */
public class Stock {
    private Integer id;

    private String code;

    private String name;

    private String cName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
}
