package com.jt.baimo.pw.service;

import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.SystemPushVo;

import java.util.Date;
import java.util.List;

/**
 * 极光推送模块
 * @author Ms.WenJing
 * @data 2019/07/30 13:52
 */
public interface JpushService {
    ResultData systemPush(String rUid, String content,Integer type);

    ResultData allPush(String content, Integer type);

    void pushSystemMessage(String jPushId, String content, Integer type, String rUid);

    void idPush(String rUid,String sUid, Integer type,String id,Object ... arguments);

    ResultData<List<SystemPushVo>> querySystemMsgList(Date lastTime, Integer size, String userId);
}
