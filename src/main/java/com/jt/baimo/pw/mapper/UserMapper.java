package com.jt.baimo.pw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.vo.MyCommonInfoVo;
import com.jt.baimo.pw.vo.UserDetailsVo;
import com.jt.baimo.pw.vo.UserInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-07-18
 */
public interface UserMapper extends BaseMapper<User> {



    UserDetailsVo userDetails(@Param("uid")String uid, @Param("userId") String userId);


}
