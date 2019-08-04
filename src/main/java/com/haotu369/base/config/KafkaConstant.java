package com.haotu369.base.config;

/**
 * @author Jian Shen
 * @version V1.0.0
 * @date 2019/8/3
 */
public class KafkaConstant {

    public enum group {
        USER_GROUP("userGroup");

        private String value;

        group (String value) {
            this.value = value;
        }

        public String getGroup() {
            return value;
        }
    }

    public enum topic {
        USER_CLICK_TOPIC("userClickTopic"),
        USER_REGISTER_TOPIC("userRegisterTopic");

        private String value;

        topic(String value) {
            this.value = value;
        }

        public String getTopic() {
            return value;
        }
    }
}
