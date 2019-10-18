package com.jt.baimo.pw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.jt.baimo.pw.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/7/19 14:14
 */
public interface CommonMapper extends BaseMapper {

    List<SelectListVo> listSelect(@Param("typeName") String typeName);

    List<SelectMultiListVo> listMultiSelect(@Param("typeName") String typeName);


    TargetCommonInfoVo getTargetCommonInfo(@Param("targetId") String targetId);

    UserSkillPwAllInfoVo getUserSkillPwAllInfo(@Param("id") Integer id);
}
