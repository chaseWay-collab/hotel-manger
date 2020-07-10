package com.example.controller;

import com.example.common.utils.DataResult;
import com.example.service.HomeService;
import com.example.service.HttpSessionService;
import com.example.vo.response.HomeRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: HomeController
 * @Description: HomeController
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/sys")
@Api(tags = "首页数据")
public class HomeController {
    @Autowired
    private HomeService homeService;
    @Autowired
    private HttpSessionService httpSessionService;

    /**
     * 主页数据
     * @param request
     * @return
     */
    @GetMapping("/home")
    @ApiOperation(value = "获取首页数据接口")
    public DataResult<HomeRespVO> getHomeInfo(HttpServletRequest request) {
        /**
         * 通过access_token拿userId
         */
        String userId = httpSessionService.getCurrentUserId();
        DataResult<HomeRespVO> result = DataResult.success();
        result.setData(homeService.getHomeInfo(userId));
        return result;
    }
}
