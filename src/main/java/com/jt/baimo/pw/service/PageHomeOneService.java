package com.jt.baimo.pw.service;

import com.jt.baimo.pw.vo.HomeOneAlbumTargetListVo;
import com.jt.baimo.pw.vo.HomeOneUserInfoVo;
import com.jt.baimo.pw.vo.ResultData;

import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/19 14:00
 */
public interface PageHomeOneService {
    ResultData<HomeOneUserInfoVo> getUserInfo(String id, String userId);


}
