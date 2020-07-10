package com.example.service;

import com.example.entity.system.SysDept;
import com.example.vo.response.DeptRespNodeVO;

import java.util.List;

/**
 * @ClassName: DeptService
 * @Description: DeptService
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface DeptService {

    SysDept addDept(SysDept vo);

    void updateDept(SysDept vo);

    SysDept detailInfo(String id);

    void deleted(String id);

    List<DeptRespNodeVO> deptTreeList(String deptId);

    List<SysDept> selectAll();
}
