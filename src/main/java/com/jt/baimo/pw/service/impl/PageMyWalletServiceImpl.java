package com.jt.baimo.pw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.reflect.TypeToken;
import com.jt.baimo.pw.constant.AliPayConfig;
import com.jt.baimo.pw.mapper.BmbRecordMapper;
import com.jt.baimo.pw.mapper.RechargeConfigMapper;
import com.jt.baimo.pw.mapper.TxbRecordMapper;
import com.jt.baimo.pw.model.BmbRecord;
import com.jt.baimo.pw.model.RechargeConfig;
import com.jt.baimo.pw.model.TxbRecord;
import com.jt.baimo.pw.model.UserBindAlipay;
import com.jt.baimo.pw.service.CommonService;
import com.jt.baimo.pw.service.PageMyWalletService;
import com.jt.baimo.pw.service.UserBindAlipayService;
import com.jt.baimo.pw.service.UserOrderService;
import com.jt.baimo.pw.util.AliPayUtil;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.WalletBmbRecordsVo;
import com.jt.baimo.pw.vo.WalletTxbInRecordsVo;
import com.jt.baimo.pw.vo.WalletTxbOutRecordsVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/19 19:12
 */
@Service
public class PageMyWalletServiceImpl implements PageMyWalletService {

    @Autowired
    private RechargeConfigMapper rechargeConfigMapper;

    @Autowired
    private BmbRecordMapper bmbRecordMapper;


    @Autowired
    private TxbRecordMapper txbRecordMapper;


    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private UserBindAlipayService userBindAlipayService;


    @Autowired
    private UserOrderService userOrderService;


    @Autowired
    private CommonService commonService;


    @Override
    public ResultData getRechargeConfigList() {
        List<RechargeConfig> rechargeConfigs = rechargeConfigMapper.selectList(null);
        return new ResultData().setData(rechargeConfigs);
    }


    @Override
    public ResultData getBmbRecords(String userId, Integer pageSize, Date lastAddTime) {
        List<BmbRecord> bmbRecords = bmbRecordMapper.selectList(new LambdaQueryWrapper<BmbRecord>()
                .lt(lastAddTime != null, BmbRecord::getAddTime, lastAddTime)
                .eq(BmbRecord::getUid, userId)
                .orderByDesc(BmbRecord::getAddTime)
                .last("limit " + pageSize));
        List<WalletBmbRecordsVo> bmbRecordsVos = modelMapper.map(bmbRecords, new TypeToken<List<WalletBmbRecordsVo>>() {
        }.getType());
        return new ResultData().setData(bmbRecordsVos);
    }


    @Override
    public ResultData bindAli(String userId, String account, String realName) {
        UserBindAlipay existUserBindAli = userBindAlipayService.getOne(new LambdaQueryWrapper<UserBindAlipay>().eq(UserBindAlipay::getUId, userId));
        UserBindAlipay newUserBindAli = new UserBindAlipay().setUId(userId).setAccount(account).setRealName(realName);
        if (existUserBindAli != null) {
            newUserBindAli.setId(existUserBindAli.getId());
        }
        userBindAlipayService.saveOrUpdate(newUserBindAli);
        return new ResultData();
    }

    @Override
    public ResultData getBindAli(String userId) {
        //获取绑定的支付宝信息
        UserBindAlipay existUserBindAli = userBindAlipayService.getOne(new LambdaQueryWrapper<UserBindAlipay>().eq(UserBindAlipay::getUId, userId));
        return new ResultData().setData(existUserBindAli);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData transfer(String userId, BigDecimal money) {
        //判断是否绑定了支付宝账号
        UserBindAlipay userAli = userBindAlipayService.getOne(new LambdaQueryWrapper<UserBindAlipay>().eq(UserBindAlipay::getUId, userId));
        Assert.isNull(userAli, "请先绑定支付宝账号再进行提现");

        Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        JSONObject result = commonService.updateSubUserTxb(userId, money);
        BigDecimal nowMoney = result.getBigDecimal("nowMoney");
        BigDecimal oldMoney = result.getBigDecimal("oldMoney");
        BigDecimal balance =  nowMoney;
        String orderNo = commonService.createOrderNo("TX", userId);

        //通过支付宝进行提现
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.SERVICE_ULR, AliPayConfig.APP_ID, AliPayConfig.APP_PRIVATE_KEY, AliPayConfig.FORMAT, AliPayConfig.CHARSET_UTF8, AliPayConfig.PUBLIC_KEY, AliPayConfig.SIGN_TYPE);
        AlipayFundTransToaccountTransferRequest transferRequest = new AlipayFundTransToaccountTransferRequest();
        AlipayFundTransToaccountTransferModel transferModel = AliPayUtil.getTransferModel(orderNo, userAli.getAccount(), userAli.getRealName(), money.toString());
        transferRequest.setBizModel(transferModel);
        ResultData resultData = new ResultData();
        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(transferRequest);
            String msg = response.getMsg();
            String subMsg = response.getSubMsg();
            if (!"Success".equals(msg)) {
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                balance=oldMoney;
                resultData.failMsgResult(subMsg);
            }
            txbRecordMapper.insert(new TxbRecord().setMoney(money).setBalance(balance).setType(-1).setOrderId(orderNo).setUid(userId).setMsg(msg).setSubMsg(subMsg));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return resultData;
    }

    @Override
    public ResultData getTxbInRecords(String userId, Integer pageSize, Date lastAddTime) {
        List<TxbRecord> txbRecords = txbRecordMapper.selectList(
                new LambdaQueryWrapper<TxbRecord>().eq(TxbRecord::getUid, userId)
                        .lt(lastAddTime != null, TxbRecord::getAddTime, lastAddTime)
                        .gt(TxbRecord::getType, 0)
                        .orderByDesc(TxbRecord::getAddTime).last("limit " + pageSize)
        );
        List<WalletTxbInRecordsVo> txbInRecordsVos = modelMapper.map(txbRecords, new TypeToken<List<WalletTxbInRecordsVo>>() {
        }.getType());
        return new ResultData().setData(txbInRecordsVos);
    }


    @Override
    public ResultData getTxbOutRecords(String userId, Integer pageSize, Date lastAddTime) {
        List<TxbRecord> txbRecords = txbRecordMapper.selectList(
                new LambdaQueryWrapper<TxbRecord>().eq(TxbRecord::getUid, userId)
                        .lt(lastAddTime != null, TxbRecord::getAddTime, lastAddTime)
                        .lt(TxbRecord::getType, 0)
                        .orderByDesc(TxbRecord::getAddTime).last("limit " + pageSize)
        );
        List<WalletTxbOutRecordsVo> txbOutRecordsVos = modelMapper.map(txbRecords, new TypeToken<List<WalletTxbOutRecordsVo>>() {
        }.getType());
        return new ResultData().setData(txbOutRecordsVos);
    }
}
