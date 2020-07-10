package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.system.SysLog;
import com.example.mapper.SysLogMapper;
import com.example.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: LogServiceImpl
 * @Description: LogServiceImpl
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * 日志分页列表
     * @param vo
     * @return
     */
    @Override
    public IPage<SysLog> pageInfo(SysLog vo) {
        Page<SysLog> page = new Page<>(vo.getPage(), vo.getLimit());
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(vo.getUsername()) ) {
            queryWrapper.like("username", vo.getUsername());
        }
        if (!StringUtils.isEmpty(vo.getOperation()) ) {
            queryWrapper.like("operation", vo.getOperation());
        }
        if (!StringUtils.isEmpty(vo.getStartTime()) ) {
            queryWrapper.gt("create_time", vo.getStartTime());
        }
        if (!StringUtils.isEmpty(vo.getEndTime()) ) {
            queryWrapper.lt("create_time", vo.getEndTime());
        }
        queryWrapper.orderByDesc("create_time");
        return sysLogMapper.selectPage(page, queryWrapper);
    }

    /**
     * 删除日志
     * @param logIds
     */
    @Override
    public void deleted(List<String> logIds) {
        sysLogMapper.deleteBatchIds(logIds);
    }
}
