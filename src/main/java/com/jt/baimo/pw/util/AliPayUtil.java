package com.jt.baimo.pw.util;

import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.jt.baimo.pw.constant.AliPayConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author szd
 * @Description: 类描述
 * @data 2019/2/25 20:05
 */
public class AliPayUtil {

    /**
     * 得到支付model
     *
     * @param outTradeNo 订单号
     * @param money      金额
     * @return
     */
    public static AlipayTradeAppPayModel getPayModel(String outTradeNo, String money) {
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("App支付");
        //todo 主题
        model.setSubject("百陌跨城支付");
        model.setOutTradeNo(outTradeNo);
        model.setTimeoutExpress(AliPayConfig.TIME_OUT);
        model.setTotalAmount(money);
        model.setProductCode("QUICK_MSECURITY_PAY");
        return model;
    }


    public static long hour(Date beforeTime, Date afterTime) {
        long diff = afterTime.getTime() - beforeTime.getTime();
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        // 计算差多少小时
        long hour = diff % nd / nh;
        return hour;
    }

    /**
     * 获得转账的Model
     *
     * @param bizNo
     * @param account
     * @param realName
     * @param amount
     * @return
     */
    public static AlipayFundTransToaccountTransferModel getTransferModel(String bizNo, String account, String realName, String amount) {
        AlipayFundTransToaccountTransferModel transferModel = new AlipayFundTransToaccountTransferModel();
        transferModel.setOutBizNo(bizNo);
        transferModel.setPayeeType("ALIPAY_LOGONID");
        transferModel.setPayeeAccount(account);
        transferModel.setAmount(amount);
        transferModel.setPayerShowName("百陌跨城-提现");
        transferModel.setRemark("百陌跨城-提现");
        transferModel.setPayeeRealName(realName);
        return transferModel;
    }


    /**
     * 得到支付model
     *
     * @param outTradeNo 订单号
     * @param money      金额
     * @return
     */
    public static AlipayTradeAppPayModel getNativePayModel(String outTradeNo, String money) {

        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //todo 主题
        model.setSubject("百陌支付");
        model.setTimeoutExpress(AliPayConfig.TIME_OUT);
        model.setStoreId("XB001");
        model.setTotalAmount(money);
        model.setOutTradeNo(outTradeNo);
        return model;
    }


    /**
     * 将支付宝通知请求的数据封装厂Map对象
     *
     * @param request
     * @return
     */
    public static Map<String, String> getNotifyRequestMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        return params;
    }


}
