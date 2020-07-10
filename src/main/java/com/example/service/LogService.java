package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.system.SysLog;

import java.util.List;

/**
 * @ClassName: LogService
 * @Description: LogService
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface LogService {

    IPage<SysLog> pageInfo(SysLog vo);

    void deleted(List<String> logIds);
}
