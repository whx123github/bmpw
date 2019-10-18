package com.jt.baimo.pw.service;

import com.jt.baimo.pw.todo.UserEditTo;
import com.jt.baimo.pw.vo.MyCommonInfoVo;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.UserSimpleInfoVo;

/**
 * @author Forever丶诺
 * @data 2019/9/18 9:11
 */
public interface PageMyService {
    ResultData<UserSimpleInfoVo> getUserSimpleInfo(String userId);

    ResultData editUserInfo(String userId, UserEditTo userEditTo);

    ResultData getCommonInfo(String userId);

    ResultData<MyCommonInfoVo> deleteMyVoice(String userId);

    ResultData<MyCommonInfoVo> updateMyVoice(String userId,String url);
}
