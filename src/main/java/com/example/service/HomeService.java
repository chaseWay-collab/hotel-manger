package com.example.service;


import com.example.vo.response.HomeRespVO;

/**
 * @ClassName: HomeService
 * @Description: HomeService
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface HomeService {

    HomeRespVO getHomeInfo(String userId);
}
