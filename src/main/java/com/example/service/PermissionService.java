package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.system.SysPermission;
import com.example.vo.response.PermissionRespNode;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: PermissionService
 * @Description: PermissionService
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface PermissionService extends IService<SysPermission> {

    List<SysPermission> getPermission(String userId);

    SysPermission addPermission(SysPermission vo);

    SysPermission detailInfo(String permissionId);

    void updatePermission(SysPermission vo);

    void deleted(String permissionId);

    List<SysPermission> selectAll();

    Set<String> getPermissionsByUserId(String userId);

    List<PermissionRespNode> permissionTreeList(String userId);

    List<PermissionRespNode> selectAllByTree();

    List<PermissionRespNode> selectAllMenuByTree(String permissionId);

}
