package com.jt.baimo.pw.service;


import com.jt.baimo.pw.vo.ResultData;

public interface SmsService {

     ResultData sendMsg(String userTel, String codeType);
}
