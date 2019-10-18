package com.jt.baimo.pw.exception;

import lombok.Data;

/**
 * Created by yb_li on 2019/1/5.
 */
@Data
public class BaimoException extends RuntimeException {

    private String status;
    private Object data;

    private  String msg;

    public BaimoException(String status, Object data ) {
        this.status = status;
        this.data = data;
    }

    public BaimoException(String status, String msg ) {
        this.status = status;
        this.msg = msg;

    }

    public BaimoException(String status, String msg , Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }



}
