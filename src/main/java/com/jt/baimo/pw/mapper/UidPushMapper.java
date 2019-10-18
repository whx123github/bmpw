package com.jt.baimo.pw.mapper;

import com.jt.baimo.pw.model.UidPush;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.baimo.pw.vo.SystemPushVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 激光推送(某个人) Mapper 接口
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-19
 */
public interface UidPushMapper extends BaseMapper<UidPush> {

    List<SystemPushVo> viewSystemList(@Param("lastTime") Date lastTime, @Param("size") Integer size, @Param("userId") String userId);
}
