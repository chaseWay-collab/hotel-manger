package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.SysRole;
import com.example.vo.request.RoleAddReqVO;
import com.example.vo.request.RoleUpdateReqVO;

import java.util.List;

/**
 * @ClassName: RoleService
 * @Description: RoleService
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface RoleService {

    SysRole addRole(RoleAddReqVO vo);

    void updateRole(RoleUpdateReqVO vo, String accessToken);

    SysRole detailInfo(String id);

    void deletedRole(String id);

    IPage<SysRole> pageInfo(SysRole vo);

    List<SysRole> getRoleInfoByUserId(String userId);

    List<String> getRoleNames(String userId);

    List<SysRole> selectAllRoles();
}
