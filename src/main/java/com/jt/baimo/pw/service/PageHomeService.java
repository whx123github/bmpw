package com.jt.baimo.pw.service;

import com.jt.baimo.pw.vo.HomeConsultantClassesVo;
import com.jt.baimo.pw.vo.ResultData;

import java.util.Date;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/18 22:41
 */
public interface PageHomeService {
    ResultData getSkillList(Integer gwTypeId);

    ResultData getUserList(String userId, Integer pageSize, Double lastDistance, Integer skillId, Integer lastUid, Integer type);

    ResultData searchUserList(String nickname, Integer pageSize, Date lastAuditTime, Integer lastUid, String userId);


    ResultData<List<HomeConsultantClassesVo>> getConsultantClasses();
}
