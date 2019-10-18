package com.jt.baimo.pw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jt.baimo.pw.mapper.AttentionMapper;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.model.Attention;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.service.AttentionService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.vo.AttentionPageListVo;
import com.jt.baimo.pw.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 关注表 服务实现类
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-07-26
 */
@Service
public class AttentionServiceImpl extends ServiceImpl<AttentionMapper, Attention> implements AttentionService {

    @Autowired
    private AttentionMapper attentionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultData addAttention(String targetId, String userId) {
        Assert.isTrue(targetId.equals(userId), "不能关注自己");
        attentionMapper.replaceAttention(targetId, userId);
        return new ResultData();
    }

    @Override
    public ResultData pageList(String userId, Integer pageSize, Integer lastAttentionId) {
        User user = userMapper.selectById(userId);
        Double lat = user.getLat(), lng = user.getLng();

        List<AttentionPageListVo> attentions = attentionMapper.pageList(userId, pageSize, lastAttentionId);
        attentions.forEach(attention -> {
            Date birthday = attention.getBirthday();
            attention.setAge(AppUtil.getAge(birthday));
            attention.setDistance(AppUtil.distance(lng, lat, attention.getLng(), attention.getLat()));
        });
        return new ResultData().setData(attentions);
    }
}
