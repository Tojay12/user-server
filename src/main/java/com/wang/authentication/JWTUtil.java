package com.wang.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wang.model.entity.User;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Smile
 * @Date: 2019-12-19 14:35
 * @Description:
 */
public class JWTUtil {
    public static final long EXPIRE_TIME = 30 * 60 * 1000L;

    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param password 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需password解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            if (token.contains(CacheConstants.TOKEN_PREFIX)) {
                token = token.replace(CacheConstants.TOKEN_PREFIX, "");
            }
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     *
     * @param username        用户名
     * @param password        用户的密码
     * @param tokenExpireTime
     * @return 加密的token
     */
    public static String sign(String username, String password, long tokenExpireTime) {
        try {
            if (tokenExpireTime == 0L) {
                tokenExpireTime = EXPIRE_TIME;
            }

            Date date = new Date(System.currentTimeMillis() + tokenExpireTime);
            Algorithm algorithm = Algorithm.HMAC256(password);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }



    /**
     * 生成令牌
     *
     * @param claims
     * @return
     */
    public static String genToken(String key,String userName, String password, Integer userId, long expire)
        throws UnsupportedEncodingException {
        if (expire == 0L) {
            expire = EXPIRE_TIME;
        }
        Date date = new Date(System.currentTimeMillis() + expire);
        Algorithm algorithm = Algorithm.HMAC256(key);

        // 设置有效期和签名=>生成令牌
        // return token;
        // 附带username信息
        return JWT.create()
                .withClaim("username", userName)
                .withClaim("password", password)
                .withClaim("userId", userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static String createToken(User loginUser,String key, long expire)
        throws UnsupportedEncodingException {
        if (expire == 0L) {
            expire = EXPIRE_TIME;
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", loginUser.getUsername());
        claims.put("password",loginUser.getPassword());
        claims.put("userId",loginUser.getId());
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 360);
        Date expiresDate = nowTime.getTime();
        Algorithm algorithm = Algorithm.HMAC256(key);
        Date date = new Date(System.currentTimeMillis() + expire);
        return JWT.create()
                .withClaim("username", loginUser.getUsername())
                .withClaim("username", loginUser.getUsername())
                .withClaim("userId", loginUser.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(expiresDate)
                .sign(algorithm);
    }

    /**
     * 校验令牌
     *
     * @param token
     */
    public static void verifyToken(String token,String key) throws UnsupportedEncodingException {
        JWT.require(Algorithm.HMAC256(key)).build().verify(token);
    }

    /**
     * 解析令牌
     *
     * @param token
     * @return
     */
    public static String parseToken(String token, String valueKey,String key)
        throws UnsupportedEncodingException {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(key)).build().verify(token);
        return decodedJWT.getClaim(valueKey).asString();
    }


    /**
     * 根据token过去过期时间
     * @param token token
     * @return 过期时间戳
     */
    public static Long getExpiredDateFromToken(String token) {
        return JWT.decode(token).getExpiresAt().getTime();
    }


    /**
     * 解析超时时间
     *
     * @param token
     * @return
     */
    public static Date getExpiresAt(String token) {
        try {
            if (token.contains(CacheConstants.TOKEN_PREFIX)) {
                token = token.replace(CacheConstants.TOKEN_PREFIX, "");
            }
            return JWT.decode(token).getExpiresAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * @param token: token
     * @param name:  名称
     * @return com.auth0.jwt.interfaces.Claim
     * @description 通过名字获取值
     * @author litianlong
     * @since 2021/5/10 13:26
     */
    public static Claim getClaimByName(String token, String name) {
        return JWT.decode(token).getClaim(name);
    }

}