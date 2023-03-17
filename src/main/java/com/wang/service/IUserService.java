package com.wang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.model.common.ResponseBean;
import com.wang.model.entity.User;
import com.wang.model.from.LoginFromUser;

/**
 *
 * @author jyw
 * @date 2018/8/9 15:44
 */
public interface IUserService  extends IService<User> {

  /**
   * 登录接口
   * @param login
   * @return
   */
  ResponseBean login(LoginFromUser login);
}
