package com.wang.controller;

import com.wang.model.common.ResponseBean;
import com.wang.model.from.LoginFromUser;
import com.wang.model.valid.group.UserLoginValidGroup;
import com.wang.service.IUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 * @author jyw
 * @date 2018/8/29 15:45
 */
@Api(tags = "用户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@PropertySource("classpath:config.properties")
public class UserController {
    
    private final IUserService userService;
    

    /**
     * 登录授权
     * @param loginFromUser 登录用户参数
     * @author jyw
     * @date 2018/8/30 16:21
     */
    @PostMapping("/login")
    public ResponseBean login(@Validated(UserLoginValidGroup.class) @RequestBody LoginFromUser loginFromUser) {
        return this.userService.login(loginFromUser);
    }

    /**
     * 测试登录
     * @param
     * @return com.wang.model.common.ResponseBean
     * @author jyw
     * @date 2018/8/30 16:18
     */
    @GetMapping("/article")
    public ResponseBean article() {
        
        return new ResponseBean(HttpStatus.OK.value(), "你是游客(You are guest)", null);
    }

    /**
     * 测试登录注解(@RequiresAuthentication和subject.isAuthenticated()返回true一个性质)
     * @param
     * @return com.wang.model.common.ResponseBean
     * @author jyw
     * @date 2018/8/30 16:18
     */
    @GetMapping("/article2")
    public ResponseBean requireAuth() {
        return new ResponseBean(HttpStatus.OK.value(), "您已经登录了(You are already logged in)", null);
    }
    
}
