package com.haotu369.base.aop;

import com.alibaba.fastjson.JSON;
import com.haotu369.base.Result;
import com.haotu369.base.annotation.AuthOperation;
import com.haotu369.base.config.KafkaConstant;
import com.haotu369.base.support.KafkaProducer;
import com.haotu369.util.CookieUtils;
import com.haotu369.util.RequestUtils;
import com.haotu369.util.TokenUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @Autowired
    private KafkaProducer kafkaProducer;

    @Pointcut("@annotation(com.haotu369.base.annotation.AuthOperation)")
    private void authAnnotation() {

    }

    @Around("authAnnotation() && @annotation(authOperation)")
    public Object doAround(ProceedingJoinPoint joinPoint, AuthOperation authOperation) throws Throwable {
        HttpServletRequest request = RequestUtils.getRequest();
        Cookie cookie = CookieUtils.getCookieByName(request, "auth-token");
        if (cookie != null) {
            String token = CookieUtils.getCookieByName(request, "auth-token").getValue();

            String userToken = (String) redisTemplate.opsForValue().get(token);

            if (userToken != null) { // Token有效
                String data =   TokenUtils.getUsernameFromToken(userToken) + "访问了" + request.getRequestURL().toString();
                kafkaProducer.sendMessage(KafkaConstant.Topic.USER_CLICK_TOPIC.getValue(), data);
                return joinPoint.proceed();
            }
        }
        HttpServletResponse response = RequestUtils.getResponse();
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
        response.getWriter().write(JSON.toJSONString(new Result().message(errorCode, errorMessage)));
    }
}
