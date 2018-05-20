package com.haotu369.base.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haotu369.base.MessageResult;
import com.haotu369.base.annotation.AuthOperation;
import com.haotu369.util.CookieUtil;
import com.haotu369.util.RequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/20
 */
@Aspect
@Component
public class AuthAop{

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.haotu369.base.annotation.AuthOperation)")
    private void authAnnotation() {

    }

    @Around("authAnnotation() && @annotation(authOperation)")
    public Object doAround(ProceedingJoinPoint joinPoint, AuthOperation authOperation) throws Throwable {
        HttpServletRequest request = RequestUtil.getRequest();
        Cookie cookie = CookieUtil.getCookieByName(request, "auth-token");
        if (cookie != null) {
            String token = CookieUtil.getCookieByName(request, "auth-token").getValue();

            String validation = (String) redisTemplate.opsForValue().get(token);

            if (validation != null) { // Token有效
                return joinPoint.proceed();
            }
        }
        HttpServletResponse response = RequestUtil.getResponse();
        setResponseMessage(response, -1, "请先登录");
        return null;
    }

    /**
     * Response返回错误信息统一调用方法 并设置content-type
     *
     * @param response
     * @param errorCode
     * @param errorMessage
     * @throws IOException
     */
    public void setResponseMessage(HttpServletResponse response ,int errorCode ,String errorMessage) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(new MessageResult().message(errorCode, errorMessage)));
    }
}
