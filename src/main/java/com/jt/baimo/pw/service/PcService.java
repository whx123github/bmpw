package com.jt.baimo.pw.service;

import com.jt.baimo.pw.vo.ResultData;

/**
 * @author Forever丶诺
 * @data 2019/9/25 10:46
 */
public interface PcService {
    ResultData adminCancelPwOrder(String id, String cancelReason, String aid);
}
