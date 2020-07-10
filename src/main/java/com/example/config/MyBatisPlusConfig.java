package com.example.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: MyBatisPlusConfig
 * @Description: 注册分页
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Configuration
public class MyBatisPlusConfig {

    /**
     * 配置mybatis-plus 分页插件
     * @return 分页
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}