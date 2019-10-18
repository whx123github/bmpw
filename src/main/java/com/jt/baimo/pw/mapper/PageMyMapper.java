package com.jt.baimo.pw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.baimo.pw.vo.MyCommonInfoVo;
import com.jt.baimo.pw.vo.UserSimpleInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author Forever丶诺
 * @data 2019/9/18 9:16
 */
public interface PageMyMapper extends BaseMapper {
    UserSimpleInfoVo getUserSimpleInfo(@Param("userId") String userId);

    MyCommonInfoVo getCommonInfo(String userId);
}
