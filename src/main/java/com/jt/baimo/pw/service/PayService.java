package com.jt.baimo.pw.service;



import com.jt.baimo.pw.vo.ResultData;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

/**
 * @author Forever丶诺
 * @data 2019/6/4 19:26
 */

public interface PayService {
    ResultData pay(String orderNo, Integer payType);



    ResultData iosPay(String orderNo, String receipt, @NotBlank String transactionId);


    String notifyUrl(HttpServletRequest request);

    ResponseEntity payNative(String orderNo, HttpServletResponse response);
}
