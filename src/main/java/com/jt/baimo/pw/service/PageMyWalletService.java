package com.jt.baimo.pw.service;

import com.jt.baimo.pw.model.RechargeConfig;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.WalletBmbRecordsVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/19 19:11
 */
public interface PageMyWalletService {
    ResultData getRechargeConfigList();

    ResultData getBmbRecords(String userId, Integer pageSize, Date lastAddTime);


    ResultData bindAli(String userId, String account, String realName);

    ResultData getTxbInRecords(String userId, Integer pageSize, Date lastAddTime);

    ResultData getTxbOutRecords(String userId, Integer pageSize, Date lastAddTime);

    ResultData getBindAli(String userId);

    ResultData transfer(String userId, BigDecimal money);
}
