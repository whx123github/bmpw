package com.jt.baimo.pw.service;

import com.jt.baimo.pw.vo.ResultData;

/**
 * @author Forever丶诺
 * @data 2019/8/2 13:57
 */
public interface ThirdPartyService {
    ResultData getToken(String uuid);
}
