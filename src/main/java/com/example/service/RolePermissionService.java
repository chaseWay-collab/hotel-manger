package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.system.SysRolePermission;
import com.example.vo.request.RolePermissionOperationReqVO;

import java.util.List;

/**
 * @ClassName: RolePermissionService
 * @Description: RolePermissionService
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface RolePermissionService extends IService<SysRolePermission> {

    int removeByRoleId(String roleId);

    List<String> getPermissionIdsByRoles(List<String> roleIds);

    void addRolePermission(RolePermissionOperationReqVO vo);

    int removeByPermissionId(String permissionId);

    List<String> getRoleIds(String permissionId);
    List<String> getPermissionIdsByRoleId(String roleId);

}
