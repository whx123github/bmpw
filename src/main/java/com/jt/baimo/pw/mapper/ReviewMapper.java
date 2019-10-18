package com.jt.baimo.pw.mapper;

import com.jt.baimo.pw.model.Review;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 认证头像语音表 Mapper 接口
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-17
 */
public interface ReviewMapper extends BaseMapper<Review> {

    void replaceReviews(@Param("reviews") List reviews, @Param("userId") String userId);

    void replaceReview(@Param("url") String url,@Param("userId") String userId,@Param("type")Integer type);

}
