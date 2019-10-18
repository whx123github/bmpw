package com.jt.baimo.pw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.vo.HomeUserListVo;
import com.jt.baimo.pw.vo.HomeUserSearchListVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/18 9:16
 */
public interface PageHomeMapper extends BaseMapper {

    List<HomeUserListVo> getUserList(@Param("user") User user, @Param("pageSize") Integer pageSize, @Param("lastDistance") Double lastDistance, @Param("lastUid") Integer lastUid, @Param("skillId") Integer skillId,@Param("gwTypeId") Integer gwTypeId);



    List<HomeUserSearchListVo> searchUserList(@Param("nickname") String nickname, @Param("pageSize")  Integer pageSize, @Param("lastAuditTime")Date lastAuditTime, @Param("lastUid") Integer lastUid,@Param("userId")  String userId);
}
