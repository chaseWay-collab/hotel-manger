package com.example.controller;


import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: IndexController
 * @Description: 返回视图
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Api(tags = "视图", value = "负责返回视图" ) // authorizations 授权
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/login")
    public String logout() {
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/index/home";
        }
        return "login";
    }

    /**
     * 进入首页
     */
    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        return "home";
    }

    /**
     * 更改密码页面
     */
    @GetMapping("/users/password")
    public String updatePassword() {
        return "users/update_password";
    }

    /**
     * 用户编辑个人信息 视图
     */
    @GetMapping("/users/info")
    public String userDetail(Model model) {
        model.addAttribute("flagType", "edit");
        return "users/user_edit";
    }

    /**
     * 菜单权限列表  视图
     */
    @GetMapping("/menus")
    public String menusList() {

        return "menus/menu_list";
    }

    /**
     * 角色列表 操作视图
     */
    @GetMapping("/roles")
    public String roleList() {
        return "roles/role_list";
    }

    /**
     * 用户列表操作 视图
     */
    @GetMapping("/users")
    public String userList() {
        return "users/user_list";
    }

    /**
     * 系统操作日志 视图
     */
    @GetMapping("/logs")
    public String logList() {
        return "logs/log_list";
    }

    /**
     * 组织机构列表 视图
     */
    @GetMapping("/depts")
    public String deptList() {
        return "depts/dept_list";
    }

    /**
     *  房间管理列表 视图
     *
     */
    @GetMapping("/rooms")
    public String roomList(){
        return "rooms/room_list";
    }

    /**
     *  楼层列表 视图
     *
     */
    @GetMapping("/floors")
    public String hotelFloorList(){
        return "floors/floor_list";
    }

    /**
     *  订单列表 视图
     *
     */
    @GetMapping("/orders")
    public String hotelOrderList(){
        return "order/order_list";
    }

    /**
     *  房型列表 视图
     *
     */
    @GetMapping("/roomType")
    public String roomTypeList(){
        return "rooms/roomType_list";
    }


    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/404")
    public String error404() {
        return "error/404";
    }

    @GetMapping("/500")
    public String error405() {
        return "error/500";
    }

    @GetMapping("/main")
    public String indexHome() {
        return "main";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
