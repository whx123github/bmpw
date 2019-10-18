package com.jt.baimo.pw.service;


import com.jt.baimo.pw.vo.ResultData;
import org.springframework.http.ResponseEntity;

/**
 * @author Forever丶诺
 * @data 2019/6/13 11:22
 */
public interface AirTicketService {
    ResultData getPortal(String userId);


    void execNotifyQt(String orderNo);

    int notifyQt(String orderNo);


}
