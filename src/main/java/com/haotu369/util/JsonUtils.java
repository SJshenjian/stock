package com.haotu369.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * @author Jian Shen
 * @version V1.0.0
 * @date 2019/9/22
 */
public class JsonUtils {

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * 转换为普通JavaBean：readValue(json,Student.class)
     * 转换为List:readValue(json,List.class
     * ).但是如果我们想把json转换为特定类型的List，比如List<Student>，就不能直接进行转换了。
     * 因为readValue(json,List .class)返回的其实是List<Map>类型，
     * 你不能指定readValue()的第二个参数是List<Student>.class，所以不能直接转换。
     * 我们可以把readValue()的第二个参数传递为Student[].class.然后使用Arrays.asList();
     * 方法把得到的数组转换为特定类型的List。 转换为Map：readValue(json,Map.class)
     * 将对象转为Map数组可以调用：List<Map<String, Object>> list2 = JsonUtils.toObject(json字符串, List.class);
     *
     * @param content 要转换的JavaBean类型
     * @param valueType 原始json字符串数据
     * @return JavaBean对象
     */
    public static <T> T toObject(String content, Class<T> valueType) {
        try {
            return ObjectMapperSingleton.OBJECT_MAPPER.getInstance().readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据转换异常：" + e.getMessage());
        }
    }

    /**
     * 将一个json字符串转为某个对象List<T>
     *
     * @param jsonContent
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> toObjectList(String jsonContent, Class<T> type) {
        try {
            JavaType javaType = getJavaType(List.class, type);
            return ObjectMapperSingleton.OBJECT_MAPPER.getInstance().readValue(jsonContent, javaType);
        } catch (Exception e) {
            throw new RuntimeException("传入数据应该是一个数组：" + e.getMessage());
        }
    }

    /**
     * 把JavaBean转换为json字符串 普通对象转换：toJson(Student) List转换：toJson(List)
     * Map转换:toJson(Map) 我们发现不管什么类型，都可以直接传入这个方法
     *
     * @param object JavaBean对象
     * @return json字符串
     */
    public static String toJson(Object object) {
        try {
            return ObjectMapperSingleton.OBJECT_MAPPER.getInstance().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("数据转换异常：" + e.getMessage());
        }
    }

    public enum ObjectMapperSingleton {
        OBJECT_MAPPER;

        private ObjectMapper objectMapper;

        ObjectMapperSingleton() {
            objectMapper = new ObjectMapper();
        }

        public ObjectMapper getInstance() {
            return objectMapper;
        }
    }

    private static JavaType getJavaType(Class<?> collectionType, Class<?> ... elementType) {
        return ObjectMapperSingleton.OBJECT_MAPPER.getInstance().getTypeFactory().constructParametricType(collectionType, elementType);
    }
}
