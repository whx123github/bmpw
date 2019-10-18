package com.jt.baimo.pw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.baimo.pw.mapper.MenuMapper;
import com.jt.baimo.pw.model.Menu;
import com.jt.baimo.pw.service.MenuService;
import com.jt.baimo.pw.vo.MenuVo;
import com.jt.baimo.pw.vo.ResultData;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ms.WenJing
 * @Description:
 * @Date 2019/9/23 19:33
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private TreeServiceImpl<MenuVo, Integer> treeService;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData queryMenu(Integer type) {
        List<Menu> menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getType,type).eq(Menu::getStatus,1));
        List<MenuVo> menusVoList = modelMapper.map(menus, new TypeToken<List<MenuVo>>() {
        }.getType());
        List<MenuVo> resultSkills = treeService.getChildTreeList(menusVoList, 0);
        return new ResultData().setData(resultSkills);
    }
}
