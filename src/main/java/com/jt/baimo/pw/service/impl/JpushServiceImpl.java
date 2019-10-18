package com.jt.baimo.pw.service.impl;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.baimo.pw.config.properties.CommonProperties;
import com.jt.baimo.pw.enumerate.AuditPushTypeEnum;
import com.jt.baimo.pw.mapper.AuditReasonMapper;
import com.jt.baimo.pw.mapper.SystemPushMapper;
import com.jt.baimo.pw.mapper.UidPushMapper;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.model.AuditReason;
import com.jt.baimo.pw.model.UidPush;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.service.JpushService;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.SystemPushVo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 极光推送模块
 *
 * @author Ms.WenJing
 * @data 2019/07/30 13:53
 */
@Service
@Slf4j
public class JpushServiceImpl implements JpushService {

    @Autowired
    private AuditReasonMapper auditReasonMapper;


    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SystemPushMapper systemPushMapper;

    @Autowired
    private UidPushMapper uidPushMapper;

    @Override
    public ResultData systemPush(String rUid, String content, Integer type) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, rUid));
        pushSystemMessage(user.getJPushId(), content, type, rUid);
        return new ResultData();
    }

    @Override
    public ResultData allPush(String content, Integer type) {
        pushMsgAll(content, type);
        return new ResultData();
    }

    /**
     * 后台审核
     *
     * @param jPushId 注册中心
     * @param content 内容
     * @param type    类型
     * @param rUid    推送人ID
     */
    @Override
    public void pushSystemMessage(String jPushId, String content, Integer type, String rUid) {
        // 配置环境
        JPushClient jpushClient = new JPushClient(commonProperties.getJPushMasterSecret(), commonProperties.getJPushAppKey(), null, ClientConfig.getInstance());
        PushPayload payload = buildAPushObject(jPushId, content, type, rUid);
        JpushTry(jpushClient, payload);
        // 推送成功后内容保存数据库
        uidPushMapper.insert(new UidPush().setContent(content).setType(type).setRUid(rUid));
    }

    /**
     * 订单推送
     *
     * @param jPushId 注册中心ID
     * @param content 推送内容
     * @param type    推送类型
     * @param rUid    接收推送人ID
     * @param sUid    发送推送人ID
     * @param id      订单ID
     */
    public void idpushSystemMessage(String jPushId, String content, Integer type, String rUid, String sUid, String id) {
        JPushClient jpushClient = new JPushClient(commonProperties.getJPushMasterSecret(), commonProperties.getJPushAppKey(), null, ClientConfig.getInstance());
        PushPayload payload = idpushbuildAPushObject(jPushId, content, type, rUid, sUid, id);
        JpushTry(jpushClient, payload);
    }

    /**
     * 订单推送-没有发送推送人
     *
     * @param jPushId 注册中心ID
     * @param content 推送内容
     * @param type    推送类型
     * @param rUid    接收推送人ID
     * @param id      订单ID
     */
    public void idapushSystemMessage(String jPushId, String content, Integer type, String rUid, String id) {
        JPushClient jpushClient = new JPushClient(commonProperties.getJPushMasterSecret(), commonProperties.getJPushAppKey(), null, ClientConfig.getInstance());
        PushPayload payload = buildAPushObject(jPushId, content, type, rUid, id);
        JpushTry(jpushClient, payload);
    }

    @Override
    public void idPush(String rUid, String sUid, Integer type, String id,Object ... arguments) {
        try {
            // 查询推送人信息
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, rUid));
            // 查询推送内容信息
            AuditReason auditReason = auditReasonMapper.selectOne(new LambdaQueryWrapper<AuditReason>().eq(AuditReason::getId, type));
            String content = MessageFormat.format(auditReason.getContent(), arguments);
            // 没有发起推送人时
            if (sUid == null) {
                idapushSystemMessage(user.getJPushId(), content, type, rUid, id);
                uidPushMapper.insert(new UidPush().setContent(content).setType(type).setRUid(rUid).setDid(id));
            } else {
                // 有发起推送人时
                idpushSystemMessage(user.getJPushId(), content, type, rUid, sUid, id);
                uidPushMapper.insert(new UidPush().setContent(content).setType(type).setRUid(rUid).setSUid(sUid).setDid(id));
            }
        } catch (Exception e) {
            log.error("推送异常", e);
        }
    }

    @Override
    public ResultData<List<SystemPushVo>> querySystemMsgList(Date lastTime, Integer size, String userId) {
        return new ResultData<List<SystemPushVo>>().setData(uidPushMapper.viewSystemList(lastTime, size, userId));
    }

    /**
     * 系统推送
     *
     * @param content 推送内容
     * @param type    推送类型
     */
    private void pushMsgAll(String content, Integer type) {
        JPushClient jpushClient = new JPushClient(commonProperties.getJPushMasterSecret(), commonProperties.getJPushAppKey(), null, ClientConfig.getInstance());
        PushPayload payload = buildAllMsgPayload(content, type);
        JpushTry(jpushClient, payload);
    }

    private void JpushTry(JPushClient jpushClient, PushPayload payload) {
        try {
            PushResult result = jpushClient.sendPush(payload);
            val statusCode = result.statusCode;
            log.info("Got result - " + result);
        } catch (APIConnectionException e) {
            log.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
        }
    }

    /**
     * 订单推送-没有发送推送人
     *
     * @param jPushId 注册中心
     * @param content 内容
     * @param type    类型
     * @param rUid    推送人ID
     * @param id      订单ID
     * @return
     */
    private PushPayload buildAPushObject(String jPushId, String content, Integer type, String rUid, String id) {
        return idpushbuildAPushObject(jPushId, content, type, rUid, null, id);
    }

    /**
     * 订单推送-有发送推送人
     *
     * @param jPushId 注册中心
     * @param content 内容
     * @param type    类型
     * @param rUid    推送人ID
     * @param id      订单ID
     * @return
     */
    private PushPayload buildAPushObject(String jPushId, String content, Integer type, String rUid, String sUid, String id) {
        return idpushbuildAPushObject(jPushId, content, type, rUid, sUid, id);
    }


    public PushPayload createPushPayload(String jPushId, String content, Integer type, Map<String, String> extras) {
        return PushPayload.newBuilder()
                // 推送平台
                .setPlatform(Platform.android_ios())
                // 推送目标
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.registrationId(jPushId))
                        .build())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtras(extras)
                                .setAlert(AuditPushTypeEnum.jPushNotice(type).getNotice()).build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtras(extras)
                                .setAlert(AuditPushTypeEnum.jPushNotice(type).getNotice()).incrBadge(1).build()).build())
                // 自定义消息
                .setMessage(Message.newBuilder()
                        // 消息内容本身
                        .setMsgContent(content)
                        .build())
                // 可选参数
                .setOptions(Options.newBuilder()
                        .setApnsProduction(commonProperties.isJPushApnsProduction())
                        .build())
                .build();
    }


    public PushPayload createAllPushPayload(String content, Integer type, Map<String, String> extras) {
        return PushPayload.newBuilder()
                // 推送平台
                .setPlatform(Platform.android_ios())
                // 推送目标
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtras(extras)
                                .setAlert(content).build())
//                                .setAlert(content).addCustom("uri_activity", "cn.jpush.android.ui.OpenClickActivity").build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtras(extras)
                                .setAlert(content).incrBadge(1).build()).build())
                // 自定义消息
                .setMessage(Message.newBuilder()
                        // 消息内容本身
                        .setMsgContent(content)
                        .build())
                // 可选参数
                .setOptions(Options.newBuilder()
                        .setApnsProduction(commonProperties.isJPushApnsProduction())
                        .build())
                .build();
    }

    /**
     * 后台审核:不审核订单时
     *
     * @param jPushId 注册中心
     * @param content 内容
     * @param type    类型
     * @param rUid    推送人ID
     * @return
     */
    private PushPayload buildAPushObject(String jPushId, String content, Integer type, String rUid) {
        Map<String, String> map = new HashMap<>();
        map.put("type", String.valueOf(type));
        map.put("currentTime", String.valueOf(System.currentTimeMillis()));
        map.put("rUid", rUid);
        map.put("content", content);
        Map<String, String> extras = JSONObject.parseObject(JSONObject.toJSONString(map), Map.class);
        return createPushPayload(jPushId, content, type, extras);
    }


    /***
     * app审核订单时:有发送推送人
     * @param jPushId 注册中心ID
     * @param content 推送内容
     * @param type 推送类型
     * @param rUid 接收推送人ID
     * @param sUid 发送推送人ID
     * @param id 订单ID
     * @return
     */
    private PushPayload idpushbuildAPushObject(String jPushId, String content, Integer type, String rUid, String sUid, String id) {

        Map<String, String> baseExtra = getBaseExtra(content, type);
        baseExtra.put("rUid", rUid);
        baseExtra.put("sUid", sUid);
        baseExtra.put("id", id);
        Map<String, String> extras = JSONObject.parseObject(JSONObject.toJSONString(baseExtra), Map.class);
        return createPushPayload(jPushId, content, type, extras);
    }

    /**
     * 系统推送
     *
     * @param content 推送内容
     * @param type    推送类型
     * @return
     */
    private PushPayload buildAllMsgPayload(String content, Integer type) {
        Map<String, String> extras = JSONObject.parseObject(JSONObject.toJSONString(getBaseExtra(content, type)), Map.class);
        return createAllPushPayload(content, type, extras);
    }


    /**
     * 获取基础的baseMap
     *
     * @param content
     * @param type
     * @return
     */
    private Map<String, String> getBaseExtra(String content, Integer type) {
        Map<String, String> map = new HashMap<>();
        map.put("type", String.valueOf(type));
        map.put("currentTime", String.valueOf(System.currentTimeMillis()));
        map.put("content", content);
        return map;
    }

}
