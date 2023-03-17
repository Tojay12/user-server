package com.wang.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.InvalidSessionStrategy;

/**
 * @author: jyw
 * @date: 2022/7/12 20:02
 * @description: 登录踢出策略
 */
public class LoginWasKickedOutExpiredStrategy implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}
