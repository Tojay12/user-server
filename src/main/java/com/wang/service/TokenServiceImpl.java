package com.wang.service;

import com.wang.authentication.CacheConstants;
import com.wang.authentication.JWTUtil;
import com.wang.model.entity.User;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author: jyw
 * @date: 2023/3/17 11:22
 * @description:
 */
@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl  {
  

  
  private final RedisService redisService;
  
  public String createToken(User loginUser, String key,long tokenExpireTime) {
        String token = "";
        try {
          token = JWTUtil.createToken(loginUser, key, tokenExpireTime);
        } catch (UnsupportedEncodingException e) {
          log.error("不支持的编码异常");
          throw new RuntimeException(e);
        }
    String oldToken = redisService.getCacheObject(CacheConstants.LOGIN_TOKEN_KEY + loginUser.getId());
    if (StringUtils.isEmpty(oldToken)){
      redisService.setCacheObject(CacheConstants.LOGIN_TOKEN_KEY + loginUser.getId(), token,
              tokenExpireTime,TimeUnit.SECONDS);
    }else {
      // 先删除之前的token  在保存新的token
      redisService.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + loginUser.getId());
      redisService.setCacheObject(CacheConstants.LOGIN_TOKEN_KEY + loginUser.getId(), token,
              tokenExpireTime,TimeUnit.SECONDS);
    }
    return token;
  }

  /**
   * 刷新token
   * @param token old token
   */
  public void refreshToken(String token) {
    long tokenExpireTime = JWTUtil.getClaimByName(token, "tokenExpireTime").asLong();
    int userId = JWTUtil.getClaimByName(token, "userId").asInt();
    long refreshTokenCurrentTime = System.currentTimeMillis() + tokenExpireTime;
    redisService.setCacheObject(CacheConstants.LOGIN_TOKEN_KEY + userId,token,
        refreshTokenCurrentTime,TimeUnit.SECONDS);
  }
  
}
