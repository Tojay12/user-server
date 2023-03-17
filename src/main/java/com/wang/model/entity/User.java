package com.wang.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wang.model.valid.group.UserEditValidGroup;
import com.wang.model.valid.group.UserLoginValidGroup;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User
 *
 * @author jyw
 * @date 2018/8/31 14:43
 */
@Data
@TableName(value = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable, UserDetails {

  private static final long serialVersionUID = 3342723124953988435L;


  public User(Integer id, String username, String password,
      Set<GrantedAuthority> grantedAuthorities) {
    this.id = id;
    this.account = username;
    this.password = password;
    this.authorities = grantedAuthorities;
  }

  /**
   * ID
   */
  private Integer id;

  /**
   * 帐号
   */
  @NotNull(message = "帐号不能为空", groups = {UserLoginValidGroup.class, UserEditValidGroup.class})
  private String account;

  /**
   * 密码
   */
  @NotNull(message = "密码不能为空", groups = {UserLoginValidGroup.class, UserEditValidGroup.class})
  private String password;

  /**
   * 昵称
   */
  @NotNull(message = "用户名不能为空", groups = {UserEditValidGroup.class})
  private String username;

  /**
   * 注册时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date regTime;

  /**
   * 登录时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date loginTime;

  @TableField(exist = false)
  private Set<GrantedAuthority> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
