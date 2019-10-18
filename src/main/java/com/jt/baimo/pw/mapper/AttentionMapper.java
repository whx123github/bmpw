package com.jt.baimo.pw.mapper;

import com.jt.baimo.pw.model.Attention;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.baimo.pw.vo.AttentionPageListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 关注表 Mapper 接口
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-17
 */
public interface AttentionMapper extends BaseMapper<Attention> {

    List<AttentionPageListVo> pageList(@Param("userId") String userId, @Param("pageSize") Integer pageSize, @Param("lastAttentionId") Integer lastAttentionId);

    void replaceAttention(@Param("targetId") String targetId,@Param("uId") String userId);

    List<String> getAttentionId(String userId);
}
