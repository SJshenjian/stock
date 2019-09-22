package com.haotu369.base.config;

/**
 * @author Jian Shen
 * @version V1.0.0
 * @date 2019/8/3
 */
public class KafkaConstant {

    public enum Topic {
        USER_CLICK_TOPIC("userClickTopic", "用户点击主题"),
        USER_REGISTER_TOPIC("userRegisterTopic", "用户注册主题"),
        PRICE_DATA_TOPIC("priceDataTopic", "行情数据主题");

        private String value;
        private String desc;

        Topic (String value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }
    }
}
