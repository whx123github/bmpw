package com.jt.baimo.pw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.reflect.TypeToken;
import com.jt.baimo.pw.mapper.MenuMapper;
import com.jt.baimo.pw.mapper.PageHomeMapper;
import com.jt.baimo.pw.mapper.SkillMapper;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.model.Menu;
import com.jt.baimo.pw.model.Skill;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.service.PageHomeService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/18 22:41
 */
@Service
public class PageHomeServiceImpl implements PageHomeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PageHomeMapper pageHomeMapper;


    @Autowired
    private MenuMapper menuMapper;


    @Override
    public ResultData getSkillList(Integer getGwTypeId) {


        List<Skill> skills = skillMapper.selectList(new LambdaQueryWrapper<Skill>().ne(Skill::getPid, 0).eq(Skill::getGwTypeId, getGwTypeId).orderByAsc(Skill::getSort));


        List<HomeSkillListVo> result = modelMapper.map(skills, new TypeToken<List<HomeSkillListVo>>() {
        }.getType());
        return new ResultData().setData(result);
    }


    @Override
    public ResultData getUserList(String userId, Integer pageSize, Double lastDistance, Integer skillId, Integer lastUid, Integer gwTypeId) {
        //获取当前用户的坐标
        User user = userMapper.selectById(userId);
        Double lat = user.getLat(), lng = user.getLng();
        List<HomeUserListVo> homeUsers = pageHomeMapper.getUserList(user, pageSize, lastDistance, lastUid, skillId, gwTypeId);
        homeUsers.forEach(homeUserVo -> {
            Date birthday = homeUserVo.getBirthday();
            homeUserVo.setAge(AppUtil.getAge(birthday));
            homeUserVo.setDistance(AppUtil.distance(lng, lat, homeUserVo.getLng(), homeUserVo.getLat()));
        });
        return new ResultData().setData(homeUsers);
    }


    @Override
    public ResultData searchUserList(String nickname, Integer pageSize, Date lastAuditTime, Integer lastUid, String userId) {
        List<HomeUserSearchListVo>  searchListVos = pageHomeMapper.searchUserList(nickname, pageSize, lastAuditTime, lastUid, userId);

        searchListVos.forEach(data -> {
            data.setAge(AppUtil.getAge(data.getBirthday()));
        });
        return new ResultData().setData(searchListVos);
    }


    @Override
    public ResultData<List<HomeConsultantClassesVo>> getConsultantClasses() {
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getStatus, 1).eq(Menu::getPid, 4));
        List<HomeConsultantClassesVo> results = modelMapper.map(menus, new TypeToken<List<HomeConsultantClassesVo>>() {
        }.getType());
        return new ResultData<List<HomeConsultantClassesVo>>().setData(results);
    }
}
