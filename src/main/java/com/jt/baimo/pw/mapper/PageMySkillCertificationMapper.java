package com.jt.baimo.pw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.baimo.pw.vo.SkillCertificationMySubmitVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author Forever丶诺
 * @data 2019/9/18 21:23
 */
public interface PageMySkillCertificationMapper extends BaseMapper {
    SkillCertificationMySubmitVo getMySubmit(@Param("userId") String userId, @Param("gwTypeId") Integer gwTypeId);
}
