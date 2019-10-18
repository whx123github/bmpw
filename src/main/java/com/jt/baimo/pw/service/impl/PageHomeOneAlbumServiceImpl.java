package com.jt.baimo.pw.service.impl;

import com.jt.baimo.pw.constant.OrderModuleCst;
import com.jt.baimo.pw.mapper.*;
import com.jt.baimo.pw.model.Album;
import com.jt.baimo.pw.model.BmbRecord;
import com.jt.baimo.pw.model.TxbRecord;
import com.jt.baimo.pw.model.UserOrder;
import com.jt.baimo.pw.service.PageHomeOneAlbumService;
import com.jt.baimo.pw.service.UserOrderService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.AlbumTargetVo;
import com.jt.baimo.pw.vo.HomeOneAlbumTargetListVo;
import com.jt.baimo.pw.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/19 14:45
 */
@Service
public class PageHomeOneAlbumServiceImpl implements PageHomeOneAlbumService {

    @Autowired
    private PageHomeOneAlbumMapper pageHomeOneAlbumMapper;


    @Autowired
    private AlbumMapper albumMapper;


    @Autowired
    private UserOrderService userOrderService;


    @Autowired
    private UserOrderMapper userOrderMapper;


    @Autowired
    private BmbRecordMapper bmbRecordMapper;

    @Autowired
    private TxbRecordMapper txbRecordMapper;

    @Override
    public ResultData<List<HomeOneAlbumTargetListVo>> getTargetList(String targetId, String userId) {
        List<HomeOneAlbumTargetListVo> albumTargetVos = pageHomeOneAlbumMapper.getTargetList(targetId, userId);
        //有价格 且 没有被解锁
        for (HomeOneAlbumTargetListVo albumTargetVo : albumTargetVos) {
            BigDecimal price = albumTargetVo.getPrice();
            if (price != null && price.compareTo(new BigDecimal(0)) != 0 && albumTargetVo.getMyId() == null) {
                albumTargetVo.setUrl(null);
            }
        }
        return new ResultData().setData(albumTargetVos);
    }



}
