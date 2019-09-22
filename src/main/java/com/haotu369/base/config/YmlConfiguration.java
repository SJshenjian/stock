package com.haotu369.base.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * 自定义yml文件加载
 *
 * @author Jian Shen
 * @version V1.0.0
 * @date 2019/9/21
 */
@Configuration
public class YmlConfiguration {

    @Bean
    public PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource[]{
                new ClassPathResource("config/stock.yml")
        });
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
}
