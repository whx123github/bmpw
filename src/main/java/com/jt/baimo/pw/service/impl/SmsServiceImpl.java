package com.jt.baimo.pw.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fcibook.quick.http.QuickHttp;
import com.jt.baimo.pw.constant.RedisPrefixCst;
import com.jt.baimo.pw.enumerate.SmsCodeEnum;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.service.CommonService;
import com.jt.baimo.pw.service.RedisYwService;
import com.jt.baimo.pw.service.SmsService;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.util.EnumUtil;
import com.jt.baimo.pw.util.RandomNumberUtil;
import com.jt.baimo.pw.util.SmsMD5;
import com.jt.baimo.pw.vo.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    private String appId = "EUCP-EMY-SMS1-2JEYK";// appId
    private String secretKey = "92B1938725FE40C4";// 密钥
    private String url = "http://bjmtn.b2m.cn/simpleinter/sendSMS";//API地址
    private String contentTemplate = "【津甜股份】验证码：{0}，用于{1}，5分钟内有效。验证码提供给他人可能导致账号被盗，请勿泄露，谨防被骗"; //短信模板


    @Autowired
    private RedisYwService redisYwService;


    @Autowired
    private UserMapper userMapper;


    @Autowired
    private CommonService commonService;

    @Override
    public ResultData sendMsg(String userTel, String smsType) {
        Assert.isTrue(smsType.equals(RedisPrefixCst.SMS_REGISTERED) && commonService.existUser(userTel), "您的手机号码已经注册，请直接登录");
        String code = RandomNumberUtil.getRandomNumber(6);
        redisYwService.setSmsType(smsType, userTel, code);
        redisYwService.setSmsEveryDayTimes(userTel);
        String desc = (String) EnumUtil.getDescByVal(smsType, SmsCodeEnum.class);
        String content = MessageFormat.format(contentTemplate, code, desc);
        String result = new QuickHttp().url(url).get().addParames(getRequestParam(userTel, content)).text();
        log.info("获取短信验证码的结果:{}", result);
        Assert.isTrue(!"SUCCESS".equalsIgnoreCase(JSON.parseObject(result).getString("code")), "获取验证码失败");
        return new ResultData();
    }


    private Map getRequestParam(String userTel, String content) {
        Map params = new HashMap<>();
        params.put("appId", appId);
        String timestamp = new DateTime().toString("yyyyMMddHHmmss");
        params.put("timestamp", timestamp);
        params.put("sign", SmsMD5.md5((appId + secretKey + timestamp).getBytes()));
        params.put("mobiles", userTel);
        params.put("content", content);
        return params;
    }


}
