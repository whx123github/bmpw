package com.jt.baimo.pw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jt.baimo.pw.constant.CommonCst;
import com.jt.baimo.pw.constant.RedisPrefixCst;
import com.jt.baimo.pw.mapper.IntimityMapper;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.model.Intimity;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.service.PageMySettingService;
import com.jt.baimo.pw.service.RedisService;
import com.jt.baimo.pw.service.RedisYwService;
import com.jt.baimo.pw.todo.MySettingIntimityTo;
import com.jt.baimo.pw.util.MD5Util;
import com.jt.baimo.pw.vo.MySettingIntimityVo;
import com.jt.baimo.pw.vo.ResultData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Forever丶诺
 * @data 2019/9/18 15:18
 */
@Service
public class PageMySettingServiceImpl implements PageMySettingService {


    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RedisService redisService;


    @Autowired
    private RedisYwService redisYwService;


    @Autowired
    private IntimityMapper intimityMapper;

    @Override
    public ResultData updatePassword(String userPwd, String userId) {
        userMapper.updateById(new User().setId(userId).setUserPwd(MD5Util.encrypt(userPwd)));
        return new ResultData();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData exit(String userId) {

        redisYwService.deleteJwtToken(userId);
        userMapper.updateById(new User().setId(userId).setJPushId("").setIsLogin(0));
        return new ResultData();
    }

    @Override
    public ResultData<MySettingIntimityVo> getPushSetting(String userId) {
        Intimity intimity = intimityMapper.selectOne(new LambdaQueryWrapper<Intimity>().eq(Intimity::getUid, userId));
        return new ResultData().setData(modelMapper.map(intimity, MySettingIntimityVo.class));
    }


    @Override
    public ResultData updatePushSetting(String userId, MySettingIntimityTo intimityTo) {
        Intimity intimity = modelMapper.map(intimityTo, Intimity.class);
        intimityMapper.update(intimity.setUid(userId), new LambdaUpdateWrapper<Intimity>().eq(Intimity::getUid, userId));
        return new ResultData();
    }
}
