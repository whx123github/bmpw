package com.jt.baimo.pw.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fcibook.quick.http.QuickHttp;

import com.jt.baimo.pw.config.RonHubConfig;
import com.jt.baimo.pw.constant.RedisPrefixCst;
import com.jt.baimo.pw.mapper.ReviewMapper;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.model.Review;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.service.RedisService;
import com.jt.baimo.pw.service.RedisYwService;
import com.jt.baimo.pw.service.ThirdPartyService;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.vo.ResultData;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.jt.baimo.pw.config.RonHubConfig.APP_KEY;
import static com.jt.baimo.pw.config.RonHubConfig.TOKEN_URL;
import static com.jt.baimo.pw.constant.FailMsgCst.RY_TOKEN_FAIL;
import static com.jt.baimo.pw.constant.RedisPrefixCst.REDIS_RC_TOKEN_PRE;


/**
 * @author Forever丶诺
 * @data 2019/8/2 13:58
 */
@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {


    @Autowired
    private UserMapper userMapper;



    @Autowired
    private RedisYwService redisYwService;


    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public ResultData getToken(String userId) {
        String rcToken =   redisYwService.getRcToken(userId);
        //从缓存中
        if (rcToken != null) {
            return new ResultData<>().oneKeyDate("rcToken", rcToken);
        }
        //获取用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, userId));
        Review review = reviewMapper.selectOne(new LambdaQueryWrapper<Review>().eq(Review::getUid, userId).eq(Review::getType, 1));


        //封装请求参数
        Map requestParams = getRequestParams(userId, user.getNickname());
        //执行请求获取结果
        QuickHttp quickHttp = getHeadHttp();
        String text = quickHttp.post().url(TOKEN_URL).addParames(requestParams).setContentType(ContentType.APPLICATION_FORM_URLENCODED).text();
        JSONObject jsonObject = JSONObject.parseObject(text);
        Assert.isTrue(jsonObject.getIntValue("code") != 200, RY_TOKEN_FAIL);

        rcToken = jsonObject.getString("token");
        redisYwService.setRcToken(userId,rcToken);
        return new ResultData<>().oneKeyDate("rcToken", rcToken);
    }


    private Map getRequestParams(String userId, String nickname) {
        //前端从另外接口获取用户信息
        Map params = new HashMap<>(3);
        params.put("userId", userId);
        params.put("name", nickname);
        params.put("portraitUri", "");
        return params;
    }

    private QuickHttp getHeadHttp() {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String signature = SecureUtil.sha1(RonHubConfig.SECRET + timeStamp + timeStamp);
        QuickHttp quickHttp = new QuickHttp().addHeader("App-Key", APP_KEY).addHeader("Nonce", timeStamp).addHeader("Timestamp", timeStamp).addHeader("Signature", signature);
        return quickHttp;
    }


}
