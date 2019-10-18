package com.jt.baimo.pw.service;

/**
 * @author Forever丶诺
 * @data 2019/7/18 10:23
 */
public interface JwtTokenService {
    String generateToken(String userId);

    String getUserId(String token);
}
