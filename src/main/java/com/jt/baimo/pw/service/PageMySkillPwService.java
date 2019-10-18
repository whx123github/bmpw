package com.jt.baimo.pw.service;

import com.jt.baimo.pw.vo.MySkillPwTargetSkillVo;
import com.jt.baimo.pw.vo.ResultData;

import java.util.Date;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/20 13:34
 */
public interface PageMySkillPwService {


    ResultData getBuyPwList(Integer pageSize, Date lastTime, String userId);

    ResultData getSellPwList(Integer pageSize, Date lastTime, String userId);

    ResultData getOnePwInfo(String id);

    ResultData sellerTakePwOrder(String id, String userId);

    ResultData sellerStartPwOrder(String id, String pwImgs, String userId);

    ResultData sellerFinishPwOrder(String id, String userId);

    ResultData buyerFinishPwOrder(String id, String userId);

    ResultData buyerCancelPwOrder(String id, Integer cancelReasonType, String cancelReason, String userId);

    ResultData<List<MySkillPwTargetSkillVo>> getTargetSkills(String targetId);
}
