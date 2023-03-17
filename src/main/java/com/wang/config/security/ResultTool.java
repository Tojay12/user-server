package com.wang.config.security;

import com.wang.enums.ResultCode;
import com.wang.util.common.JsonResult;

/**
 * @author: jyw
 * @date: 2022/7/10 1:07
 * @description:
 */
public class ResultTool {

    public static JsonResult success() {
        return new JsonResult(true);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(true, data);
    }

    public static JsonResult fail() {
        return new JsonResult(false);
    }

    public static JsonResult fail(ResultCode resultEnum) {
        return new JsonResult(false, resultEnum);
    }
    public static JsonResult fail(ResultCode resultEnum,String msg) {
        return new JsonResult(false, resultEnum,msg);
    }

    public static JsonResult failMsg(String msg) {
        return new JsonResult(false, msg);
    }
}
