package com.wang.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * Role
 * @author jyw
 * @date 2018/8/31 14:42
 */
@Data

@TableName(value = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 6382925944937625109L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

}
