package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.SysRole;
import com.example.exception.MyException;
import com.example.exception.code.BaseResponseCode;
import com.example.mapper.SysRoleMapper;
import com.example.service.*;
import com.example.vo.response.PermissionRespNode;
import com.example.vo.request.RoleAddReqVO;
import com.example.vo.request.RolePermissionOperationReqVO;
import com.example.vo.request.RoleUpdateReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ClassName: RoleServiceImpl
 * @Description: RoleServiceImpl
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private HttpSessionService httpSessionService;

    /**
     * 新增角色
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole addRole(RoleAddReqVO vo) {

        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(vo, sysRole);
        sysRole.setCreateTime(new Date());
        int count = sysRoleMapper.insert(sysRole);
        if (count != 1) {
            throw new MyException(BaseResponseCode.OPERATION_ERRO);
        }
        if (null != vo.getPermissions() && !vo.getPermissions().isEmpty()) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
        }

        return sysRole;
    }

    /**
     * 更新角色
     * @param vo
     * @param accessToken
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRole(RoleUpdateReqVO vo, String accessToken) {
        SysRole sysRole = sysRoleMapper.selectById(vo.getId());
        if (null == sysRole) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        SysRole update = new SysRole();
        BeanUtils.copyProperties(vo, update);

        update.setUpdateTime(new Date());
        int count = sysRoleMapper.updateById(update);
        if (count != 1) {
            throw new MyException(BaseResponseCode.OPERATION_ERRO);
        }

        rolePermissionService.removeByRoleId(sysRole.getId());
        if (null != vo.getPermissions() && !vo.getPermissions().isEmpty()) {
            RolePermissionOperationReqVO reqVO = new RolePermissionOperationReqVO();
            reqVO.setRoleId(sysRole.getId());
            reqVO.setPermissionIds(vo.getPermissions());
            rolePermissionService.addRolePermission(reqVO);
            //刷新权限
            httpSessionService.refreshRolePermission(sysRole.getId());
        }

    }

    /**
     * 角色详情
     * @param id
     * @return
     */
    @Override
    public SysRole detailInfo(String id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (sysRole == null) {
            log.error("传入 的 id:{}不合法", id);
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        List<PermissionRespNode> permissionRespNodes = permissionService.selectAllByTree();
        Set<String> checkList = new HashSet<>(rolePermissionService.getPermissionIdsByRoleId(sysRole.getId()));
        setheckced(permissionRespNodes, checkList);
        sysRole.setPermissionRespNodes(permissionRespNodes);
        return sysRole;
    }

    /**
     * 递归遍历树节点
     * @param list
     * @param checkList
     */
    private void setheckced(List<PermissionRespNode> list, Set<String> checkList) {

        for (PermissionRespNode node : list) {

            if (checkList.contains(node.getId()) && (node.getChildren() == null || node.getChildren().isEmpty())) {
                node.setChecked(true);
            }
            setheckced((List<PermissionRespNode>) node.getChildren(), checkList);

        }
    }

    /**
     * 删除角色
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletedRole(String id) {
        sysRoleMapper.deleteById(id);

        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id").eq("role_id", id);
        rolePermissionService.removeByRoleId(id);
        userRoleService.removeByRoleId(id);
        //刷新权限
        httpSessionService.refreshRolePermission(id);
    }

    /**
     * 分页列表
     * @param vo
     * @return
     */
    @Override
    public IPage<SysRole> pageInfo(SysRole vo) {
        Page<SysRole> page = new Page<>(vo.getPage(), vo.getLimit());
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(vo.getName())) {
            queryWrapper.like("name", vo.getName());
        }
        if (!StringUtils.isEmpty(vo.getStartTime()) ) {
            queryWrapper.gt("create_time", vo.getStartTime());
        }
        if (!StringUtils.isEmpty(vo.getEndTime()) ) {
            queryWrapper.lt("create_time", vo.getEndTime());
        }
        if (!StringUtils.isEmpty(vo.getStatus())) {
            queryWrapper.eq("status", vo.getStatus());
        }
        queryWrapper.orderByDesc("create_time");
        return sysRoleMapper.selectPage(page, queryWrapper);
    }

    /**
     * 根据用户id获取角色
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> getRoleInfoByUserId(String userId) {
        List<String> roleIds = userRoleService.getRoleIdsByUserId(userId);
        if (roleIds.isEmpty()) {
            return null;
        }
        return sysRoleMapper.selectBatchIds(roleIds);
    }

    /**
     * 根据用户id获取用户拥有的角色名称
     * @param userId
     * @return
     */
    @Override
    public List<String> getRoleNames(String userId) {
        List<SysRole> sysRoles = getRoleInfoByUserId(userId);
        if (null == sysRoles || sysRoles.isEmpty()) {
            return null;
        }

        List<String> list = new ArrayList<>();
        for (SysRole sysRole : sysRoles) {
            list.add(sysRole.getName());
        }
        return list;
    }

    @Override
    public List<SysRole> selectAllRoles() {
        return sysRoleMapper.selectList(null);
    }

}
