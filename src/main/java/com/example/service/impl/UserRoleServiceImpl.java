package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.system.SysUserRole;
import com.example.mapper.SysUserRoleMapper;
import com.example.service.UserRoleService;
import com.example.vo.request.UserRoleOperationReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserRoleServiceImpl
 * @Description: UserRoleServiceImpl
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Service
public class UserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements UserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 根据角色id删除用户角色
     * @param roleId
     * @return
     */
    @Override
    public int removeByRoleId(String roleId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        return sysUserRoleMapper.delete(queryWrapper);
    }

    /**
     * 根据用户id获取拥有的角色列表
     * @param userId
     * @return
     */
    @Override
    public List<String> getRoleIdsByUserId(String userId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("role_id").eq("user_id", userId);
        return sysUserRoleMapper.selectObjs(queryWrapper);
    }

    /**
     * 新增用户角色
     * @param vo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUserRoleInfo(UserRoleOperationReqVO vo) {
        if (vo.getRoleIds() == null || vo.getRoleIds().isEmpty()) {
            return;
        }
        Date createTime = new Date();
        List<SysUserRole> list = new ArrayList<>();
        for (String roleId : vo.getRoleIds()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setCreateTime(createTime);
            sysUserRole.setUserId(vo.getUserId());
            sysUserRole.setRoleId(roleId);
            list.add(sysUserRole);
        }
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", vo.getUserId());
        sysUserRoleMapper.delete(queryWrapper);
        //批量插入
        this.saveBatch(list);
    }

    /**
     * 根据用户id移除用户角色
     * @param userId
     * @return
     */
    @Override
    public int removeByUserId(String userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return sysUserRoleMapper.delete(queryWrapper);
    }

    /**
     * 根据角色id表获取用户id
     * @param roleIds
     * @return
     */
    @Override
    public List<String> getUserIdsByRoleIds(List<String> roleIds) {

        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id").in("role_id", roleIds);
        return sysUserRoleMapper.selectObjs(queryWrapper);
    }

    /**
     * 根据角色id获取拥有的拥有的用户
     * @param roleId
     * @return
     */
    @Override
    public List<String> getUserIdsByRoleId(String roleId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id").eq("role_id", roleId);
        return sysUserRoleMapper.selectObjs(queryWrapper);
    }
}
