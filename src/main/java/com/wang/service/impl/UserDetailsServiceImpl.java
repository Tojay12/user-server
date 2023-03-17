package com.wang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wang.enums.StatusCode;
import com.wang.exception.CustomException;
import com.wang.mapper.PermissionMapper;
import com.wang.mapper.UserMapper;
import com.wang.mapper.UserRoleMapper;
import com.wang.model.entity.User;
import com.wang.model.entity.UserRole;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author: jyw
 * @date: 2023/3/16 17:21
 * @description:
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserMapper userMapper;
  private final PermissionMapper permissionMapper;
  private final UserRoleMapper userRoleMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (StringUtils.isEmpty(username)) {
      log.info("用户名或者密码错误!", username);
      throw new CustomException(StatusCode.WrongAccountOrPassword.getMsg());
    }
    //根据用户名查询用户
    User loginUser = userMapper.selectOne(
        new LambdaQueryWrapper<User>().eq(User::getAccount, username));
    if (loginUser == null) {
      log.info("登录用户：{} 不存在.", username);
      throw new CustomException(StatusCode.WrongAccountOrPassword.getMsg());
    }
    List<UserRole> userRoles = userRoleMapper.selectList(
        new LambdaQueryWrapper<UserRole>().in(UserRole::getUserId, loginUser.getId()));
    if (CollectionUtils.isEmpty(userRoles)) {
      log.info("登录用户：{} 未绑定角色", username);
      throw new CustomException("用户未绑定角色，登录失败", StatusCode.USER_ACCOUNT_DISABLE.getCode());
    }

    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    //获取该用户所拥有的权限
    List<String> checkPermissions = permissionMapper.selectByUserIdQueryPermission(loginUser.getId());
    if (CollectionUtils.isEmpty(checkPermissions)) {
      throw new CustomException("当前用户未绑定权限");
    } else {
      // 声明用户授权
      checkPermissions.forEach(checkPermission -> {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(checkPermission);
        grantedAuthorities.add(grantedAuthority);
      });
    }
    return new User(loginUser.getId(), loginUser.getAccount(), loginUser.getPassword(),
        grantedAuthorities);

  }
}
