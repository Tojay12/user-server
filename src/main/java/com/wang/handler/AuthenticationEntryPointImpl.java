package com.wang.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author: jyw
 * @date: 2022/7/10 7:20
 * @description: 认证失败处理类 返回未授权
 */
@Component
public class AuthenticationEntryPointImpl extends BaseHandler implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        String msg = StringUtils.format("请求访问：[%s]，认证失败，无法访问系统资源", request.getRequestURI());
        returnJSON(response, msg);
    }

}
