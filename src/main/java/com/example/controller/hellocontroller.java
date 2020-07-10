package com.example.controller;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * @program: springboot-manger-test
 * @description: 测试类
 * @author: [咖啡]
 **/
public class hellocontroller {

    @Autowired
    DataSource dataSource;

    @Test
    public void test(){
        //System.out.println(dataSource.getClass());
        Logger logger = LoggerFactory.getLogger(hellocontroller.class);
        logger.info("Hello World");
    }


}
