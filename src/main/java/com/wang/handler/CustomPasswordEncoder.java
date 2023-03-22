package com.wang.handler;

import com.wang.enums.StatusCode;
import com.wang.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: jyw
 * @date: 2022/7/10 10:10
 * @description: 自定义密码管理器
 */
@Slf4j
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        if (charSequence == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        if (encodedPassword == null || encodedPassword.length() == 0) {
            log.warn("Empty encoded password");
            return false;
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
        if (!matches) {
            throw new CustomException(StatusCode.WrongAccountOrPassword.getMsg());
        }
        return matches;
    }






}
