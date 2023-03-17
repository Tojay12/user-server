package com.wang.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * UserRole
 * @author jyw
 * @date 2018/8/31 14:43
 */
@Data
@TableName(value = "user_role")
public class UserRole implements Serializable {

    private static final long serialVersionUID = -3397516891053950951L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

}
