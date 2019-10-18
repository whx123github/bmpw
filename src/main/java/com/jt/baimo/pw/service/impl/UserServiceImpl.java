package com.jt.baimo.pw.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.baimo.pw.constant.FailMsgCst;
import com.jt.baimo.pw.mapper.IntimityMapper;
import com.jt.baimo.pw.mapper.ReviewMapper;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.model.Intimity;
import com.jt.baimo.pw.model.Review;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.service.CommonService;
import com.jt.baimo.pw.service.JwtTokenService;
import com.jt.baimo.pw.service.RedisYwService;
import com.jt.baimo.pw.service.UserService;
import com.jt.baimo.pw.todo.LocationTo;
import com.jt.baimo.pw.todo.UserEditTo;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.util.MD5Util;
import com.jt.baimo.pw.vo.LoginResultVo;
import com.jt.baimo.pw.vo.ResultData;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-07-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private CommonService commonService;


    @Autowired
    private ReviewMapper reviewMapper;


    @Autowired
    private JwtTokenService jwtTokenService;


    @Autowired
    private RedisYwService redisYwService;


    @Autowired
    private IntimityMapper intimityMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData<LoginResultVo> register(String userTel, String userPwd, String identifyCode, String jPushId, String registerIP, String placeId) {
        //判断验证码对不对
        redisYwService.validSmsCodeRegister(userTel, identifyCode);

        //防止重复注册
        Assert.isTrue(commonService.existUser(userTel), FailMsgCst.TEL_EXIST_MSG);

        //进行注册
        User user = new User().setUserTel(userTel).setUserPwd(MD5Util.encrypt(userPwd))
                .setStatus(1).setIsLogin(1).setUid(redisYwService.getInitUid())
                .setJPushId(jPushId).setRegisterIP(registerIP).setPlaceId(placeId)
                .setNickname(AppUtil.replace(userTel, "xxx"));
        userMapper.insert(user);

        intimityMapper.insert(new Intimity().setUid(user.getId()));
        //返回用户的信息
        return new ResultData<LoginResultVo>().setData(getLoginReturnInfo(user));
    }


    @Override
    public ResultData updateSex(String userId, Integer sex) {
        userMapper.update(new User().setSex(sex).setStatus(2), new LambdaUpdateWrapper<User>().eq(User::getId, userId).eq(User::getStatus, 1));
        return new ResultData();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData initInfo(String userId, UserEditTo userEditTo) {
        //更新用户信息
        User user = modelMapper.map(userEditTo, User.class).setId(userId).setStatus(3);
        userMapper.updateById(user);

        //审核表添加信息
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review().setType(1).setUrl(user.getHeaderUrl()).setUid(userId));
        reviews.add(new Review().setType(4).setUrl(user.getNickname()).setUid(userId));
        String voiceUrl = user.getVoiceUrl();
        if (!AppUtil.isBankAndNull(voiceUrl)) {
            reviews.add(new Review().setType(2).setUrl(voiceUrl).setUid(userId));
        }
        String presentation = user.getPresentation();
        if (!AppUtil.isBankAndNull(presentation)) {
            reviews.add(new Review().setType(5).setUrl(presentation).setUid(userId));
        }

        //添加需要校验的信息
        reviewMapper.replaceReviews(reviews, userId);
        return new ResultData();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData pwdLogin(String userTel, String userPwd, String jPushId) {
        //校验手机号和密码是否匹配
        User result = userMapper.selectOne(new QueryWrapper<User>().setEntity(new User().setUserTel(userTel)));
        Assert.isNull(result, "你尚未注册，请立即注册");
        Assert.isNotEquals(result.getUserPwd(), MD5Util.encrypt(userPwd), "您输入的账号或者密码错误");
        this.updateById(new User().setId(result.getId()).setIsLogin(1).setJPushId(jPushId));
        //登录产生token,返回给服务端
        return new ResultData<LoginResultVo>().setData(getLoginReturnInfo(result));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData<LoginResultVo> forgetPwd(String userTel, String userPwd, String identifyCode, String jPushId) {
        //校验验证码
        redisYwService.validSmsCodeForget(userTel, identifyCode);

        //校验用户存不在
        User exist = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUserTel, userTel));
        Assert.isNull(exist, FailMsgCst.UPDATE_FAIL);

        // 进行修改操作
        int update = userMapper.update(new User().setUserPwd(MD5Util.encrypt(userPwd))
                        .setIsLogin(1).setJPushId(jPushId)
                , new LambdaUpdateWrapper<User>().eq(User::getUserTel, userTel));
        Assert.isZero(update, FailMsgCst.UPDATE_FAIL);
        return new ResultData().setData(getLoginReturnInfo(exist));
    }


    @Override
    public ResultData uploadLocation(String userId, LocationTo locationTo) {
        User user = modelMapper.map(locationTo, User.class).setId(userId);
        userMapper.updateById(user);
        return new ResultData();
    }

    private LoginResultVo getLoginReturnInfo(User userInfo) {
        String userId = userInfo.getId();
        redisYwService.setLoginKey(userId);
        LoginResultVo loginResultVo = modelMapper.map(userInfo, LoginResultVo.class)
                .setUserId(userId).setToken(jwtTokenService.generateToken(userId));
        return loginResultVo;
    }


    @Test
    public void testMethod(String oldStr, String replaceContent) {
        StringBuffer buffer = new StringBuffer(oldStr);
        StringBuffer x = buffer.replace(3, 7, replaceContent);
        System.out.println(x.toString());

    }

}
