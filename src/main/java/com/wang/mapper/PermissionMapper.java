package com.wang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.model.entity.Permission;
import com.wang.model.entity.Role;
import java.util.List;

/**
 * PermissionMapper
 * @author jyw
 * @date 2018/8/31 14:42
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据Role查询Permission
     * @param roleDto
     * @return java.util.List<com.wang.model.PermissionDto>
     * @author jyw
     * @date 2018/8/31 11:30
     */
    List<Permission> findPermissionByRole(Role roleDto);

    /**
     * 按用户ID查询权限*
     * @param userId 用户id
     * @return
     */
    List<String> selectByUserIdQueryPermission(Integer userId);
}
