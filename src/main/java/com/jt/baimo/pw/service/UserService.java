package com.jt.baimo.pw.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.todo.LocationTo;
import com.jt.baimo.pw.todo.UserEditTo;
import com.jt.baimo.pw.vo.LoginResultVo;
import com.jt.baimo.pw.vo.ResultData;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-07-18
 */
public interface UserService extends IService<User> {


    ResultData<LoginResultVo> register(String userTel, String userPwd, String identifyCode, String jPushId, String registerIP, String placeId);


    ResultData updateSex(String userId, Integer sex);


    ResultData initInfo(String userId,  UserEditTo userEditTo);


    ResultData pwdLogin(String userTel, String userPwd, String jPushId);


    ResultData forgetPwd(String userTel, String userPwd, String identifyCode, String jPushId);


    ResultData uploadLocation(String userId, LocationTo locationTo);
}
