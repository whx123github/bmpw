package com.jt.baimo.pw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.baimo.pw.mapper.*;
import com.jt.baimo.pw.model.*;
import com.jt.baimo.pw.service.DynamicService;
import com.jt.baimo.pw.service.JpushService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ms.WenJing
 * @Description:
 * @Date 2019/9/17 18:51
 */
@Service
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic> implements DynamicService {

    @Autowired
    private DynamicMapper dynamicMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AttentionMapper attentionMapper;

    @Autowired
    private JpushService jpushService;

    @Autowired
    private UidPushMapper uidPushMapper;

    @Autowired
    private DynamicPraiseMapper dynamicPraiseMapper;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private AuthenticationMapper authenticationMapper;

    protected static final String CONTENT = "发布了新动态，快去看看";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultData<Dynamic> addDynamic(String content, String picture, String userId) {
        Assert.isTrue(AppUtil.isBankAndNull(content) && AppUtil.isBankAndNull(picture), "不能都为空");
        // 发布动态
        dynamicMapper.insert(new Dynamic().setContent(content).setPicture(picture).setUid(userId));
        // 查询出关注我的ID
//        List<String> attentionIds = attentionMapper.getAttentionId(userId);
//        if (attentionIds.size() == 0){
//            return new ResultData();
//        }
//        if (attentionIds.size() > 0) {
//            jpushService.pushSystemMessage(attentionIds, user.getNickname() + CONTENT, 11, userId);
//            for (String item:attentionIds) {
//                uidPushMapper.insert(new UidPush().setContent(content).setType(11).setSUid(userId).setRUid(item));
//            }
//        }
        return new ResultData();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultData<Dynamic> deleteDynamic(Integer id, String userId) {
        dynamicMapper.delete(new LambdaQueryWrapper<Dynamic>().eq(Dynamic::getUid, userId).eq(Dynamic::getId, id));
        dynamicPraiseMapper.delete(new LambdaQueryWrapper<DynamicPraise>().eq(DynamicPraise::getDynamicId, id).eq(DynamicPraise::getUid, userId));
        return new ResultData();
    }

    @Override
    public ResultData<UserDetailsVo> userDetails(String uid, String userId) {
        UserDetailsVo userDetailsVo = userMapper.userDetails(uid, userId);
        UserDetailsVo userDetails = userDetailsVo.setAge(AppUtil.getAge(userDetailsVo.getBirthday()));
        return new ResultData().setData(userDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultData<Attention> userAttention(String uid, String userId) {
        Assert.isTrue(uid.equals(userId), "不能关注自己");
        attentionMapper.insert(new Attention().setUid(userId).setTargetId(uid));
        return new ResultData();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultData<Attention> userUnsubscribe(String uid, String userId) {
        attentionMapper.delete(new LambdaQueryWrapper<Attention>().eq(Attention::getUid, userId).eq(Attention::getTargetId, uid));
        return new ResultData();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultData<DynamicPraise> dynamicPraise(Integer id, String userId) {
        //todo 插入的时候 判断有没有已经点赞过了 把正式环境重复的点赞删除
        Integer count = dynamicPraiseMapper.selectCount(new LambdaQueryWrapper<DynamicPraise>().eq(DynamicPraise::getDynamicId, id).eq(DynamicPraise::getUid, userId));
        if (count > 0) {
            return new ResultData();
        }
        dynamicPraiseMapper.insert(new DynamicPraise().setUid(userId).setDynamicId(id));
        return new ResultData();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultData<DynamicPraise> cancelLike(Integer id, String userId) {
        dynamicPraiseMapper.delete(new LambdaQueryWrapper<DynamicPraise>().eq(DynamicPraise::getUid, userId).eq(DynamicPraise::getDynamicId, id));
        return new ResultData();
    }

    @Override
    public ResultData<List<DynamicAllVo>> serviceAllList(Integer lastId, Integer size, String uid, String userId) {
        User user = userMapper.selectById(userId);
        // 查询出所有动态列表
        List<DynamicAllVo> list = dynamicMapper.serviceAllList(lastId, size, uid, userId);

        Double myLat = user.getLat(), myLng = user.getLng();

        list.forEach(
                item -> {
                    item.setDistance(AppUtil.distance(myLng, myLat, item.getLng(), item.getLat()));
                    item.setAge(AppUtil.getAge(item.getBirthday()));
                }
        );
        return new ResultData().setData(list);
    }
}
