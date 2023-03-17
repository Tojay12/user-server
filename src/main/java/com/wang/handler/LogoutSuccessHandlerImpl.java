package com.wang.handler;

import com.wang.authentication.CacheConstants;
import com.wang.authentication.JWTUtil;
import com.wang.service.RedisService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author: jyw
 * @date: 2022/7/10 7:25
 * @description: 自定义退出处理类 返回成功
 */
@Configuration
@RequiredArgsConstructor
public class LogoutSuccessHandlerImpl extends BaseHandler implements LogoutSuccessHandler {
 
    private final RedisService redisService;

    @Value("${jwtKey}")
    private String key;
    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String requestToken = getRequestToken(request);

        if (StringUtils.isNotBlank(requestToken)) {
            // 删除用户记录
            String userId = JWTUtil.parseToken(requestToken, "userId",key);
            redisService.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + userId);
        }
        returnJSON(response, "退出成功");
    }
}
