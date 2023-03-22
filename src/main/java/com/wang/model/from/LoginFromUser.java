package com.wang.model.from;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author: jyw
 * @date: 2023/3/17 10:07
 * @description:
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoginFromUser {

  /**
   * 登录用户名
   */
  private String username;

  /**
   * 登录密码
   */
  private String password;

}
