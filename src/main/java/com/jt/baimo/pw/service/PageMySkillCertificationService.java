package com.jt.baimo.pw.service;

import com.jt.baimo.pw.todo.AuthenticationTo;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.SkillCertificationConsultantClassesVo;

import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/18 18:21
 */
public interface PageMySkillCertificationService {
    ResultData getCertificationStatus(String userId, Integer gwTypeId);

    ResultData getSkillList(Integer gwTypeId);

    ResultData addSkillCertification(String userId, AuthenticationTo authenticationTo);

    ResultData getMySubmit(String userId, Integer gwTypeId);

    ResultData<List<SkillCertificationConsultantClassesVo>> getConsultantClasses();

    ResultData hasRealName(String userId);
}
