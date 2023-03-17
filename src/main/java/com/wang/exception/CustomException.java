package com.wang.exception;

import com.wang.enums.StatusCode;

/**
 * 自定义异常(CustomException)
 * @author jyw
 * @date 2018/8/30 13:59
 */
public class CustomException extends RuntimeException {
    private String msg;
    private int code = 500;
    
    public CustomException(String msg) {
        super(msg);
        this.msg = msg;
    }
    public CustomException(StatusCode result) {
        super(result.getMsg());
        this.msg = result.getMsg();
        this.code = result.getCode();
    }
    
    public CustomException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }
    
    public CustomException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
    
    public CustomException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }


}
