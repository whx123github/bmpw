package com.jt.baimo.pw.exception;

import lombok.Data;

/**
 * @author Forever丶诺
 * @data 2019/7/30 9:33
 */
@Data
public class YwException extends RuntimeException {

    private static final long serialVersionUID = 8991936138918015370L;

    public YwException(String message) {
        super(message);
    }
}
