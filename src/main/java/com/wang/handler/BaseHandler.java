package com.wang.handler;

import com.alibaba.fastjson.JSON;
import com.wang.authentication.CacheConstants;
import com.wang.enums.ResultCode;
import com.wang.model.common.ResponseBean;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: jyw
 * @date: 2022/7/10 7:26
 * @description:
 */
public class BaseHandler {
    protected void returnJSON(ServletResponse servletResponse, String msg) throws IOException {
        servletResponse.setContentType("application/json; charset=utf-8");
        servletResponse.setCharacterEncoding("UTF-8");
        OutputStream out = servletResponse.getOutputStream();
        out.write(JSON.toJSONString(ResponseBean.failMsg(ResultCode.UNAUTHORIZED,msg)).getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
    /**
     * 获取请求的token
     */
    protected String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader(CacheConstants.HEADER);

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(CacheConstants.HEADER);
        }
        if (StringUtils.isNotEmpty(token) && token.startsWith(CacheConstants.TOKEN_PREFIX)) {
            token = token.replace(CacheConstants.TOKEN_PREFIX, "");
        }
        return token;
    }
}
