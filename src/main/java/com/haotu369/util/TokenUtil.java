package com.haotu369.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/20
 */
public class TokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtil.class);
    private static final String TOKEN_SING = "haotu369";

    /**
     * 根据用户信息生成Token
     *
     * @param userInfo
     * @return
     */
    public static String builderToken(String userInfo) {
        SecretKey secretKey = new SecretKeySpec(TOKEN_SING.getBytes(), "HS256");
        JwtBuilder builder = Jwts.builder().setPayload(userInfo)
                .signWith(SignatureAlgorithm.HS256, secretKey);
        return builder.compact();
    }

    /**
     * 解析Token令牌
     *
     * @param token
     * @return
     */
    public static Claims parseToken(String token) {
        SecretKey secretKey = new SecretKeySpec(TOKEN_SING.getBytes(), "HS256");
        Claims claims = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
