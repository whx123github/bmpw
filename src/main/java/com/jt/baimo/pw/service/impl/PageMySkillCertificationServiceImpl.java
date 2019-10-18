package com.jt.baimo.pw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.baimo.pw.mapper.AuthenticationMapper;
import com.jt.baimo.pw.mapper.MenuMapper;
import com.jt.baimo.pw.mapper.PageMySkillCertificationMapper;
import com.jt.baimo.pw.mapper.SkillMapper;
import com.jt.baimo.pw.model.Authentication;
import com.jt.baimo.pw.model.Menu;
import com.jt.baimo.pw.model.Skill;
import com.jt.baimo.pw.service.AuthenticationService;
import com.jt.baimo.pw.service.PageMySkillCertificationService;
import com.jt.baimo.pw.todo.AuthenticationTo;
import com.jt.baimo.pw.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/18 18:22
 */
@Service
@Slf4j
public class PageMySkillCertificationServiceImpl implements PageMySkillCertificationService {


    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private PageMySkillCertificationMapper pageMySkillCertificationMapper;

    @Autowired
    private AuthenticationMapper authenticationMapper;

    @Autowired
    private AuthenticationService authenticationService;


    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private TreeServiceImpl<SkillVo, Integer> treeService;


    @Autowired
    private MenuMapper menuMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData getCertificationStatus(String userId, Integer gwTypeId) {
        // 目前每个大分类下只有1个 认证
        Authentication authentication = authenticationMapper.selectOne(new LambdaQueryWrapper<Authentication>().eq(Authentication::getUid, userId).eq(Authentication::getGwTypeId, gwTypeId));
        if (authentication == null) {
            return new ResultData().oneKeyDate("status", -1);
        }
        return new ResultData().oneKeyDate("status", authentication.getStatus());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData getSkillList(Integer gwTypeId) {

        List<Skill> skills = skillMapper.selectList(new LambdaQueryWrapper<Skill>().eq(Skill::getGwTypeId, gwTypeId).orderByAsc(Skill::getSort));
        List<SkillVo> skillVoList = modelMapper.map(skills, new TypeToken<List<SkillVo>>() {
        }.getType());
        List<SkillVo> resultSkills = treeService.getChildTreeList(skillVoList, 0);
        return new ResultData().setData(resultSkills);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData addSkillCertification(String userId, AuthenticationTo authenticationTo) {
        if(authenticationTo.getGwTypeId()==null){
            authenticationTo.setGwTypeId(10);
        }


        //没有填写身份证号的处理
        List<Authentication> otherAuthentications = authenticationMapper.selectList(new LambdaQueryWrapper<Authentication>().ne(Authentication::getStatus, 3).eq(Authentication::getUid, userId));
        if (otherAuthentications != null && otherAuthentications.size() != 0) {
            Authentication otherAuthentication = otherAuthentications.get(0);
            authenticationTo.setRealName(otherAuthentication.getRealName()).setIdCard(otherAuthentication.getIdCard());
        }

        Authentication exist = authenticationService.getOne(new LambdaQueryWrapper<Authentication>().eq(Authentication::getUid, userId).eq(Authentication::getGwTypeId, authenticationTo.getGwTypeId()));

        //新增新的
        Authentication authentication = modelMapper.map(authenticationTo, Authentication.class).setUid(userId).setStatus(1).setAuditReason("");
        if (exist != null) {
            authentication.setId(exist.getId());
        }
        authenticationService.saveOrUpdate(authentication);
        return new ResultData();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData getMySubmit(String userId, Integer gwTypeId) {
        SkillCertificationMySubmitVo data = pageMySkillCertificationMapper.getMySubmit(userId,gwTypeId);
        return new ResultData().setData(data);
    }


    @Override
    public ResultData<List<SkillCertificationConsultantClassesVo>> getConsultantClasses() {
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getStatus, 1).eq(Menu::getPid, 4).orderByAsc(Menu::getSort));
        List<SkillCertificationConsultantClassesVo> results = modelMapper.map(menus, new TypeToken<List<SkillCertificationConsultantClassesVo>>() {
        }.getType());
        return new ResultData<List<SkillCertificationConsultantClassesVo>>().setData(results);
    }

    @Override
    public ResultData hasRealName(String userId) {
        Integer count = authenticationMapper.selectCount(new LambdaQueryWrapper<Authentication>().ne(Authentication::getStatus, 3).eq(Authentication::getUid, userId));
        return new ResultData().oneKeyDate("hasRealName", count);
    }
}
