package com.jt.baimo.pw.service;

import com.jt.baimo.pw.model.Banner;
import com.jt.baimo.pw.vo.ResultData;

import java.util.List;

public interface BannerService {
    ResultData<List<Banner>> queryBannerList(Integer type);
}
