package com.jt.baimo.pw.service.impl;


import com.jt.baimo.pw.config.properties.JwtProperties;
import com.jt.baimo.pw.constant.CommonCst;
import com.jt.baimo.pw.constant.RedisPrefixCst;
import com.jt.baimo.pw.service.JwtTokenService;
import com.jt.baimo.pw.service.RedisService;
import com.jt.baimo.pw.service.RedisYwService;
import com.jt.baimo.pw.util.RandomNumberUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <P>jwt token的工具类</P>
 * <pre>
 *     jwt的claim里一般包含以下几种数据
 *     1. iss -- token的发行者
 *     2. sub -- 该JWT所面向的用户
 *     3. aud -- 接收该JWT的一方
 *     4. exp -- token的实效时间
 *     5. nbf -- 在此时间段之前，不会被处理
 *     6. iat -- jwt发布时间
 *     7. jti -- jwt唯一标识，防止重复使用
 * </pre>
 * <p>
 * <p>
 *
 * @author Forever丶诺
 * @date 2019/1/8
 */
@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Autowired
    private JwtProperties jwtProperties;


    @Autowired
    private RedisService redisService;


    @Autowired
    private RedisYwService redisYwService;

    /**
     * 根据userId生成token
     */
    @Override
    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(jwtProperties.getMd5Key(), getRandomKey());
        return doGenerateToken(claims, userId);
    }


    /**
     * 生成token
     */
    private String doGenerateToken(Map<String, Object> claims, String userId) {
        //生成token
        String token = Jwts.builder()
                .setClaims(claims)
                .setId(userId)  //用户id
                .setIssuedAt(new Date()) //签名时间
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret()) //设置签名 (参数1:签名方式, 参数2:私钥)
                .compact();

        //同时redis中存取
        redisYwService.setJwtToken(userId, token);
        return token;
    }


    /**
     * 从token中解析出用户的id
     */
    @Override
    public String getUserId(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody().getId();
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * 获取混淆MD5签名用的随机字符串
     */
    public String getRandomKey() {


        return RandomNumberUtil.getRandomNumber(jwtProperties.getRandomNumberLength());
    }


}
