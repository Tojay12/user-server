package com.wang.exception;

import com.wang.enums.StatusCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * token错误异常类
 *
 * @author litianlong
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TokenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 505;


    public TokenException(StatusCode result) {
        super(result.getMsg());
        this.msg = result.getMsg();
        this.code = result.getCode();
    }

}
