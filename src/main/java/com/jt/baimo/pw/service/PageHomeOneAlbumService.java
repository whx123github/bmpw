package com.jt.baimo.pw.service;

import com.jt.baimo.pw.vo.HomeOneAlbumTargetListVo;
import com.jt.baimo.pw.vo.ResultData;

import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/19 14:45
 */
public interface PageHomeOneAlbumService {
    ResultData<List<HomeOneAlbumTargetListVo>> getTargetList(String targetId, String userId);


}
