package com.wang.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * Permission
 *
 * @author jyw
 * @date 2018/8/31 14:41
 */
@Data
@TableName(value = "permission")
public class Permission implements Serializable {

  private static final long serialVersionUID = -8834983208597107688L;

  /**
   * ID
   */
  private Integer id;

  /**
   * 资源名称
   */
  private String name;

  /**
   * 权限代码字符串
   */
  private String perCode;

  /**
   * 权限url
   */
  private String url;
}
