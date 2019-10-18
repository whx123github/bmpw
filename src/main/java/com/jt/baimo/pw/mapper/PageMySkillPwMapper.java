package com.jt.baimo.pw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.baimo.pw.vo.MySkillPwBuyPwListVo;
import com.jt.baimo.pw.vo.MySkillPwOnePwInfoVo;
import com.jt.baimo.pw.vo.MySkillPwSellPwListVo;
import com.jt.baimo.pw.vo.MySkillPwTargetSkillVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/20 13:35
 */
public interface PageMySkillPwMapper extends BaseMapper {


    List<MySkillPwBuyPwListVo> getBuyPwList(@Param("pageSize") Integer pageSize, @Param("lastTime")Date lastTime, @Param("userId") String userId, @Param("recentlyTime") Date recentlyTime);

    List<MySkillPwSellPwListVo> getSellPwList(@Param("pageSize") Integer pageSize, @Param("lastTime")Date lastTime, @Param("userId") String userId, @Param("recentlyTime") Date recentlyTime);

    MySkillPwOnePwInfoVo getOnePwInfo(@Param("id") String id);

    List<MySkillPwTargetSkillVo> getTargetSkills(@Param("targetId") String targetId);
}
