package com.jt.baimo.pw.service;

import com.jt.baimo.pw.model.Attention;
import com.jt.baimo.pw.model.Dynamic;
import com.jt.baimo.pw.model.DynamicPraise;
import com.jt.baimo.pw.vo.*;

import java.util.List;

public interface DynamicService {
    ResultData<Dynamic> addDynamic(String content, String picture, String userId);

    ResultData<Dynamic> deleteDynamic(Integer id, String userId);

    ResultData<UserDetailsVo> userDetails(String uid, String userId);

    ResultData<Attention> userAttention(String uid, String userId);

    ResultData<Attention> userUnsubscribe(String uid, String userId);

    ResultData<DynamicPraise> dynamicPraise(Integer id, String userId);

    ResultData<DynamicPraise> cancelLike(Integer id, String userId);

    ResultData<List<DynamicAllVo>> serviceAllList(Integer lastId, Integer size,String uid, String userId);
}
