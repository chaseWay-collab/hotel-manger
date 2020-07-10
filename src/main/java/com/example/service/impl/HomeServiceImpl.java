package com.example.service.impl;

import com.example.entity.system.SysDept;
import com.example.entity.system.SysUser;
import com.example.service.DeptService;
import com.example.service.HomeService;
import com.example.service.PermissionService;
import com.example.service.UserService;
import com.example.vo.response.HomeRespVO;
import com.example.vo.response.PermissionRespNode;
import com.example.vo.response.UserInfoRespVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: HomeServiceImpl
 * @Description: HomeServiceImpl
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 主页信息
     * @param userId
     * @return
     */
    @Override
    public HomeRespVO getHomeInfo(String userId) {
        //System.out.println("[HomeServiceImpl]getHomeInfo(30): " + userId);
        SysUser sysUser = userService.detailInfo(userId);
        UserInfoRespVO vo = new UserInfoRespVO();
        if (sysUser != null) {
            BeanUtils.copyProperties(sysUser, vo);
            SysDept sysDept = deptService.detailInfo(sysUser.getDeptId());
            if (sysDept != null) {
                vo.setDeptId(sysDept.getId());
                vo.setDeptName(sysDept.getName());
            }
        }
        List<PermissionRespNode> menus = permissionService.permissionTreeList(userId);
        //System.out.println("[HomeServiceImpl]getHomeInfo(45): " + menus);
        HomeRespVO respVO = new HomeRespVO();
        respVO.setMenus(menus);
        respVO.setUserInfo(vo);
        return respVO;
    }
}
