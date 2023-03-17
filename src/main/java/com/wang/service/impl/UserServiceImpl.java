package com.wang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.enums.StatusCode;
import com.wang.mapper.UserMapper;
import com.wang.model.common.ResponseBean;
import com.wang.model.entity.User;
import com.wang.model.from.LoginFromUser;
import com.wang.service.IUserService;
import com.wang.service.TokenServiceImpl;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author jyw
 * @date 2018/8/9 15:45
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
  
  @Value("${jwtKey}")
  private String key;
  @Value("${refreshTokenExpireTime}")
  private Long refreshTokenExpireTime;
  private final AuthenticationManager authenticationManager;
  private final TokenServiceImpl tokenService;
  @Override
  public ResponseBean login(LoginFromUser login) {
//    QueryWrapper<CheckVerifyCode> queryWrapper = new QueryWrapper<>();
//    queryWrapper.lambda().eq(CheckVerifyCode::getUuid, user.getUuid());
//    List<CheckVerifyCode> list = checkVerifyCodeService.list(queryWrapper);
//    if (CollectionUtils.isEmpty(list)) {
//      return ResponseBean.failMsg("验证码错误");
//    }
//    CheckVerifyCode verifyCode = list.get(0);
//    if (!user.getCode().equals(verifyCode.getCode())){
//      return ResponseBean.failMsg("验证码计算错误");
//    }
//    if (verifyCode.getExpireTime().getTime() < LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()) {
//      return ResponseBean.failMsg("验证码已失效");
//    }
//    // 删除验证码
//    checkVerifyCodeService.remove(new LambdaQueryWrapper<CheckVerifyCode>().eq(CheckVerifyCode::getUuid, user.getUuid()));

    // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
    String encode = new BCryptPasswordEncoder().encode(login.getPassword());
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(login.getAccount(),login.getPassword()));
    User loginUser = (User) authentication.getPrincipal();
    if (ObjectUtils.isEmpty(loginUser)) {
      log.warn("用户名或密码错误!");
      return ResponseBean.failMsg(StatusCode.WrongAccountOrPassword.getMsg());
    }
    String token = tokenService.createToken(loginUser, key, refreshTokenExpireTime);
    Map<String, String> map = new HashMap<>(2);
    map.put("token", token);
    map.put("userName", loginUser.getUsername());
    long loginTime  = System.currentTimeMillis();
    log.info("用户名：{}，登陆账户成功,登陆时间为： {}", loginUser.getAccount(),loginTime);
    return new ResponseBean(200,"登录成功",map);
  }
}
