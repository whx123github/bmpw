package com.jt.baimo.pw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.baimo.pw.mapper.BannerMapper;
import com.jt.baimo.pw.model.Banner;
import com.jt.baimo.pw.service.BannerService;
import com.jt.baimo.pw.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ms.WenJing
 * @Description:
 * @Date 2019/9/19 9:49
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public ResultData<List<Banner>> queryBannerList(Integer type) {
        return new ResultData().setData(bannerMapper.selectList(new LambdaQueryWrapper<Banner>().eq(Banner::getStatus,1).eq(Banner::getType,type).orderByDesc(Banner::getSort)));
    }
}
