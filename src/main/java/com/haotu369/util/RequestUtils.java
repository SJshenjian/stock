package com.haotu369.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/20
 */
public class RequestUtils {

    /**
     * 获取上下文Request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取上下文Response
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }


}
