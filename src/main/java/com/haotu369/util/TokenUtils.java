package com.haotu369.util;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.User;
import io.jsonwebtoken.*;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/20
 */
public class TokenUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtils.class);
    private static final String TOKEN_SING = "haotu369";

    /**
     * 生成Token
     *
     * @param data
     * @return
     */
    public static String buildToken(String data) {
        SecretKey secretKey = new SecretKeySpec(TOKEN_SING.getBytes(), "HS256");
        JwtBuilder builder = Jwts.builder().setPayload(data)
                .signWith(SignatureAlgorithm.HS256, secretKey);
        return builder.compact();
    }

    /**
     * 解析Token令牌
     *
     * @param token json存储格式
     * @return
     */
    public static Claims parseToken(String token) {
        SecretKey secretKey = new SecretKeySpec(TOKEN_SING.getBytes(), "HS256");
        Claims claims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    /**
     * 解析Token令牌
     *
     * @param token 平文本格式
     * @return
     */
    public static String parsePlaintextToken(String token) {
        SecretKey secretKey = new SecretKeySpec(TOKEN_SING.getBytes(), "HS256");
        String result = Jwts.parser().setSigningKey(secretKey)
                .parsePlaintextJws(token).getBody();
        return result;
    }

    /**
     * 从token中获取用户名
     *
     * @return
     */
    public static String getUsernameFromToken(@NotNull String token) {
        String username = null;
        try {
            username = String.valueOf(parseToken(token).get("username"));
        } catch (UnsupportedJwtException ex) {
            username = parsePlaintextToken(token);
        }
        return username;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("shenjian");

        String token = TokenUtils.buildToken(JSONObject.toJSONString(user));
        Claims claims = TokenUtils.parseToken(token);

        Assert.assertEquals(user.getUsername(), claims.get("username"));

        String plaintextToken = TokenUtils.buildToken("沈健");
        Assert.assertEquals("沈健" , TokenUtils.parsePlaintextToken(plaintextToken));
        Assert.assertEquals("沈健" , TokenUtils.getUsernameFromToken(plaintextToken));
    }
}
