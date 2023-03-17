package com.wang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.model.entity.Role;
import com.wang.model.entity.User;
import java.util.List;

/**
 * RoleMapper
 * @author jyw
 * @date 2018/8/31 14:42
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据User查询Role
     * @param userDto
     * @return java.util.List<com.wang.model.RoleDto>
     * @author jyw
     * @date 2018/8/31 11:30
     */
    List<Role> findRoleByUser(User userDto);
}
