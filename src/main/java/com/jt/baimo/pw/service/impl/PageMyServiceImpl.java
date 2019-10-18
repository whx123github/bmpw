package com.jt.baimo.pw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.baimo.pw.mapper.PageMyMapper;
import com.jt.baimo.pw.mapper.ReviewMapper;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.model.Review;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.service.PageMyService;
import com.jt.baimo.pw.todo.UserEditTo;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.MyCommonInfoVo;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.UserSimpleInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/18 9:11
 */
@Service
public class PageMyServiceImpl implements PageMyService {


    @Autowired
    private PageMyMapper pageMyMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData<UserSimpleInfoVo> getUserSimpleInfo(String userId) {
        UserSimpleInfoVo userInfo = pageMyMapper.getUserSimpleInfo(userId);
        Date birthday = userInfo.getBirthday();
        userInfo.setAge(AppUtil.getAge(birthday)).setConstellation(AppUtil.getConstellation(birthday));
        return new ResultData().setData(userInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData editUserInfo(String userId, UserEditTo userEditTo) {
        //更新用户信息
        User existUser = userMapper.selectById(userId);
        User user = modelMapper.map(userEditTo, User.class).setId(userId);

        //更新需要校验的
        List<Review> reviews = new ArrayList<>();
        if (!StringUtils.equals(existUser.getHeaderUrl(),user.getHeaderUrl())) {
            reviews.add(new Review().setType(1).setUrl(user.getHeaderUrl()).setUid(userId));
        }

        if (!StringUtils.equals(existUser.getNickname(),user.getNickname())) {
            reviews.add(new Review().setType(4).setUrl(user.getNickname()).setUid(userId));
        }

        String newVoiceUrl = user.getVoiceUrl();
        if (!StringUtils.equals(existUser.getVoiceUrl(), newVoiceUrl)) {
            //如果新的是空的话就删除之前的
            if (AppUtil.isBankAndNull(newVoiceUrl)) {
                reviewMapper.delete(new LambdaQueryWrapper<Review>().eq(Review::getUid, existUser.getId()).eq(Review::getType, 2));
            } else {
                reviews.add(new Review().setType(2).setUrl(newVoiceUrl));
            }
        }

        String newPresentation = user.getPresentation();
        if (!StringUtils.equals(existUser.getPresentation(), newPresentation)) {
            //如果新的是空的话就删除之前的
            if (AppUtil.isBankAndNull(newPresentation)) {
                reviewMapper.delete(new LambdaQueryWrapper<Review>().eq(Review::getUid, existUser.getId()).eq(Review::getType, 5));
            } else {
                reviews.add(new Review().setType(5).setUrl(newPresentation));
            }
        }

        if (reviews.size() > 0) {
            reviewMapper.replaceReviews(reviews, userId);
        }

        userMapper.updateById(user.setStatus(3));
        return new ResultData();
    }


    @Override
    @Transactional
    public ResultData<MyCommonInfoVo> deleteMyVoice(String userId) {
        userMapper.updateById(new User().setVoiceUrl("").setId(userId));
        reviewMapper.delete(new LambdaQueryWrapper<Review>().eq(Review::getUid, userId).eq(Review::getType, 2));
        return new ResultData<>();
    }

    @Override
    @Transactional
    public ResultData<MyCommonInfoVo> updateMyVoice(String userId, String url) {
        userMapper.updateById(new User().setVoiceUrl(url).setId(userId));
        reviewMapper.replaceReview(url,userId,2);
        return new ResultData<>();
    }

    @Override
    public ResultData getCommonInfo(String userId) {
        MyCommonInfoVo commonInfo = pageMyMapper.getCommonInfo(userId);

        return new ResultData<>().setData(commonInfo);
    }
}
