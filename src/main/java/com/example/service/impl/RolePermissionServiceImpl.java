package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.system.SysRolePermission;
import com.example.mapper.SysRolePermissionMapper;
import com.example.service.RolePermissionService;
import com.example.vo.request.RolePermissionOperationReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: RolePermissionServiceImpl
 * @Description: RolePermissionServiceImpl
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements RolePermissionService {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    /**
     * 移除角色权限
     * @param roleId
     * @return
     */
    @Override
    public int removeByRoleId(String roleId) {
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return sysRolePermissionMapper.delete(queryWrapper);
    }

    /**
     * 根据角色id获取多个角色的权限列表
     * @param roleIds
     * @return
     */
    @Override
    public List<String> getPermissionIdsByRoles(List<String> roleIds) {

        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("permission_id").in("role_id", roleIds);
        return sysRolePermissionMapper.selectObjs(queryWrapper);
    }

    /**
     * 新增角色权限
     * @param vo
     */
    @Override
    public void addRolePermission(RolePermissionOperationReqVO vo) {

        Date createTime = new Date();
        List<SysRolePermission> list = new ArrayList<>();
        for (String permissionId : vo.getPermissionIds()) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setCreateTime(createTime);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermission.setRoleId(vo.getRoleId());
            list.add(sysRolePermission);
        }
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", vo.getRoleId());
        sysRolePermissionMapper.delete(queryWrapper);
        this.saveBatch(list);
    }

    /**
     * 根据权限id移除角色权限
     * @param permissionId
     * @return
     */
    @Override
    public int removeByPermissionId(String permissionId) {

        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("permission_id", permissionId);
        return sysRolePermissionMapper.delete(queryWrapper);
    }

    /**
     * 根据权限id获取角色列表
     * @param permissionId
     * @return
     */
    @Override
    public List<String> getRoleIds(String permissionId) {

        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("role_id").eq("permission_id", permissionId);
        return sysRolePermissionMapper.selectObjs(queryWrapper);
    }

    /**
     * 根据角色id获取角色权限树
     * @param roleId
     * @return
     */
    @Override
    public List<String> getPermissionIdsByRoleId(String roleId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("permission_id").eq("role_id", roleId);
        return sysRolePermissionMapper.selectObjs(queryWrapper);
    }
}
