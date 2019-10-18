package com.jt.baimo.pw.service.impl;

import com.jt.baimo.pw.mapper.PageHomeOneMapper;
import com.jt.baimo.pw.service.PageHomeOneService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.AlbumTargetVo;
import com.jt.baimo.pw.vo.HomeOneAlbumTargetListVo;
import com.jt.baimo.pw.vo.HomeOneUserInfoVo;
import com.jt.baimo.pw.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/19 14:00
 */
@Service
public class PageHomeOneServiceImpl implements PageHomeOneService {




    @Autowired
    private PageHomeOneMapper pageHomeOneMapper;


    @Override
    public ResultData<HomeOneUserInfoVo> getUserInfo(String targetId, String userId) {
        HomeOneUserInfoVo data = pageHomeOneMapper.getUserInfo(targetId, userId);
        data.setAge(AppUtil.getAge(data.getBirthday()));
        return new ResultData<HomeOneUserInfoVo>().setData(data);
    }



}
