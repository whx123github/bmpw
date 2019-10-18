package com.jt.baimo.pw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.baimo.pw.vo.HomeOneUserInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author Forever丶诺
 * @data 2019/9/19 14:03
 */
public interface PageHomeOneMapper extends BaseMapper {
    HomeOneUserInfoVo getUserInfo(@Param("targetId") String targetId, @Param("userId") String userId);
}
