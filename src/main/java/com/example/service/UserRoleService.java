package com.example.service;


import com.example.vo.request.UserRoleOperationReqVO;

import java.util.List;

/**
 * @ClassName: UserRoleService
 * @Description: UserRoleService
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface UserRoleService {

    int removeByRoleId(String roleId);

    List<String> getRoleIdsByUserId(String userId);


    void addUserRoleInfo(UserRoleOperationReqVO vo);

    int removeByUserId(String userId);


    List<String> getUserIdsByRoleIds(List<String> roleIds);

    List<String> getUserIdsByRoleId(String roleId);
}
