package com.haotu369.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/20
 */
public class CookieUtil {

    /**
     * 存储Cookie
     *
     * @param response
     * @param name
     * @param value
     * @param expiry 有效时间，单位为秒
     */
    public static void saveCookie(HttpServletResponse response, String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/"); // 在同一应用服务器内共享
        if (expiry > 0) {
            cookie.setMaxAge(expiry);
        }
        response.addCookie(cookie);
    }

    /**
     * 获取Cookie
     *
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = getCookies(request);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        }
        return null;
    }

    /**
     * 封装Cookie到Map中
     *
     * @param request
     * @return
     */
    public static Map<String, Cookie> getCookies(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie: cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 删除所有Cookie
     */
    public static void removeAllCookies() {
        Cookie[] cookies =  RequestUtil.getRequest().getCookies();
        HttpServletResponse response = RequestUtil.getResponse();

        for (Cookie cookie: cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
}