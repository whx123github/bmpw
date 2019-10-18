package com.jt.baimo.pw.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.jt.baimo.pw.model.Attention;
import com.jt.baimo.pw.vo.ResultData;

/**
 * <p>
 * 关注表 服务类
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-07-26
 */
public interface AttentionService extends IService<Attention> {

    ResultData pageList(String userId, Integer pageSize, Integer lastAttentionId);

    ResultData addAttention(String targetId, String userId);
}
