package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.system.SysUser;
import com.example.vo.request.*;
import com.example.vo.response.LoginRespVO;
import com.example.vo.response.UserOwnRoleRespVO;

import java.util.List;

/**
 * @ClassName: UserService
 * @Description: UserService
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface UserService extends IService<SysUser> {

    String register(RegisterReqVO vo);

    LoginRespVO login(LoginReqVO vo);

    void logout();

    void updateUserInfo(UserUpdateReqVO vo, String operationId);

    IPage<SysUser> pageInfo(SysUser vo);

    SysUser detailInfo(String userId);

    void addUser(UserAddReqVO vo);


    void updatePwd(UpdatePasswordReqVO vo, String userId);

    List<SysUser> getUserListByDeptIds(List<String> deptIds);

    void deletedUsers(List<String> userIds,String operationId);

    UserOwnRoleRespVO getUserOwnRole(String userId);

    void setUserOwnRole(String userId,List<String> roleIds);

    void updateUserInfoMy(UserUpdateReqVO vo, String userId);

}
