package com.jt.baimo.pw.service;


import com.jt.baimo.pw.model.Intimity;
import com.jt.baimo.pw.todo.MySettingIntimityTo;
import com.jt.baimo.pw.vo.MySettingIntimityVo;
import com.jt.baimo.pw.vo.ResultData;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Forever丶诺
 * @data 2019/8/1 18:03
 */
public interface PageMySettingService {


    ResultData updatePassword(String userPwd, String userId);



    ResultData exit(String userId);

    ResultData<MySettingIntimityVo> getPushSetting(String userId);

    ResultData updatePushSetting(String userId, MySettingIntimityTo intimity);
}
