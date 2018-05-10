package com.haotu369.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/10
 */
public class ContextPathUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextPathUtil.class);

    /**
     * 获取上下文路径
     *
     * @param resource
     * @return
     */
    public static String getContextPath(String resource) {
        String path = ContextPathUtil.class.getClassLoader().getResource(resource).getPath();
        if (null == path) {
            LOGGER.error("文件 " + resource + "路径获取失败");
        }
        if (path.startsWith("/")) {
            path = path.replaceFirst("/", "");
        }
        return path;
    }
}
