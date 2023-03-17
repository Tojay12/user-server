package com.wang.filter;

import com.wang.authentication.CacheConstants;
import com.wang.authentication.JWTUtil;
import com.wang.enums.StatusCode;
import com.wang.exception.CustomException;
import com.wang.exception.TokenException;
import com.wang.model.entity.User;
import com.wang.service.RedisService;
import com.wang.service.TokenServiceImpl;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * @author: jyw
 * @date: 2022/7/10 7:32
 * @description: token过滤器 验证token有效性
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;


    private final TokenServiceImpl tokenService;

    private final RedisService redisService;
    
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private  HandlerExceptionResolver resolver;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            String uri = request.getRequestURI();
            List<String> white = Arrays.asList("/code", "/user/login", "/notPass/downloadNotPass",
                    "/reportrecord/upload");
            for (String item : white) {
                if (uri.contains(item)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
    
            //从header中获取token
            String requestToken = request.getHeader(CacheConstants.HEADER);
    
            //如果header中不存在token，则从参数中获取token
            if (StringUtils.isBlank(requestToken)) {
                requestToken = request.getParameter(CacheConstants.HEADER);
            }
            if (StringUtils.isNotEmpty(requestToken) && requestToken.startsWith(
                    CacheConstants.TOKEN_PREFIX)) {
                requestToken = requestToken.replace(CacheConstants.TOKEN_PREFIX, "");
            }
            if (StringUtils.isNotBlank(requestToken) && ObjectUtils.isEmpty(getAuthentication())) {
                if (requestToken.length() == 6) {
                    log.warn("非法请求:{}", requestToken);
                    resolver.resolveException(request, response, null,
                            new TokenException(StatusCode.INVALID_PARAM));
                    return;
                }
    
                String userId = JWTUtil.getClaimByName(requestToken, "userId").asString();
                Object checkUserToken = redisService.getCacheObject(
                        CacheConstants.LOGIN_TOKEN_KEY + userId);
                if (checkUserToken == null) {
                    log.warn("账户已下线，请重新登录{}", requestToken);
                    resolver.resolveException(request, response, null,
                            new TokenException(StatusCode.LOGIN_WAS_KICKED_OUT));
                    return;
                }
                //else if (checkUserToken.getExpireTime().getTime() < System.currentTimeMillis()) {
//                    log.warn("token {} 失效，请重新登录", requestToken);
//                    resolver.resolveException(request, response, null, new TokenException(StatusCode.TOKEN_EXPIRED));
//                    return;
//                } else if (checkUserToken.getExpireTime().getTime() - System.currentTimeMillis() <= refreshTokenThreefold(requestToken)) {
//                    log.info("刷新token {}", requestToken);
//                    tokenService.refreshToken(requestToken);
//                }
                    String userName = JWTUtil.getClaimByName(requestToken, "username").asString();
                    User userDetails = (User) userDetailsService.loadUserByUsername(userName);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            requestToken, userDetails, userDetails.getAuthorities());
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                chain.doFilter(request, response);
           
        }catch(Exception e){
            log.error("出现异常{}", e.getMessage());
            resolver.resolveException(request, response, null,
                    new CustomException(StatusCode.CurrUserHasNotPermission));
        }
    }
    
    private long refreshTokenThreefold(String token) {
        Long tokenExpireTime = JWTUtil.getClaimByName(token, "tokenExpireTime").asLong();
        return tokenExpireTime / 3;
    }
    
    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}