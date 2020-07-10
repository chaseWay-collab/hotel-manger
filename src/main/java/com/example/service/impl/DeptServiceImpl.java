package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.utils.CodeUtil;
import com.example.common.utils.Constants;
import com.example.entity.system.SysDept;
import com.example.entity.system.SysUser;
import com.example.exception.MyException;
import com.example.exception.code.BaseResponseCode;
import com.example.mapper.SysDeptMapper;
import com.example.service.DeptService;
import com.example.service.RedisService;
import com.example.service.UserService;
import com.example.vo.response.DeptRespNodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: DeptServiceImpl
 * @Description: DeptServiceImpl
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Service
@Slf4j
public class DeptServiceImpl implements DeptService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private UserService userService;

    /**
     * 新增部门
     * @param vo
     * @return
     */
    @Override
    public SysDept addDept(SysDept vo) {
        String relationCode;
        long result = redisService.incrby(Constants.DEPT_CODE_KEY, 1);
        //System.out.println("[DeptServiceImpl]addDept(40) result= " + result);
        String deptCode = CodeUtil.deptCode(String.valueOf(result), 6, "0");
        SysDept parent = sysDeptMapper.selectById(vo.getPid());
        if (vo.getPid().equals("0")) {
            relationCode = deptCode;
        } else if (null == parent) {
            log.error("传入的 pid:{}不合法", vo.getPid());
            throw new MyException(BaseResponseCode.DATA_ERROR);
        } else {
            relationCode = parent.getRelationCode() + deptCode;
        }
        vo.setCreateTime(new Date());
        vo.setDeptNo(deptCode);
        vo.setRelationCode(relationCode);
        int count = sysDeptMapper.insert(vo);
        if (count != 1) {
            throw new MyException(BaseResponseCode.OPERATION_ERRO);
        }
        return vo;
    }

    /**
     * 更新部门
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDept(SysDept vo) {

        SysDept sysDept = sysDeptMapper.selectById(vo.getId());
        if (null == sysDept) {
            log.error("传入 的 id:{}不合法", vo.getId());
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        SysDept update = new SysDept();
        BeanUtils.copyProperties(vo, update);
        update.setUpdateTime(new Date());
        int count = sysDeptMapper.updateById(update);
        if (count != 1) {
            throw new MyException(BaseResponseCode.OPERATION_ERRO);
        }

        /**
         * 层级发生变化后
         */
        if (!StringUtils.isEmpty(vo.getPid()) && !vo.getPid().equals(sysDept.getPid())) {
            SysDept parent = sysDeptMapper.selectById(vo.getPid());
            if (!vo.getPid().equals("0") && null == parent) {
                log.error("传入 的 pid:{}不合法", vo.getId());
                throw new MyException(BaseResponseCode.DATA_ERROR);
            }
            SysDept oldParent = sysDeptMapper.selectById(sysDept.getPid());

            String oldRelationCode;
            String newRelationCode;
             // 根目录降到其他目录
            if (sysDept.getPid().equals("0")) {
                oldRelationCode = sysDept.getDeptNo();
                newRelationCode = parent.getRelationCode() + sysDept.getDeptNo();
            } else if (vo.getPid().equals("0")) {//其他目录升级到跟目录
                oldRelationCode = sysDept.getRelationCode();
                newRelationCode = sysDept.getDeptNo();
            } else {
                oldRelationCode = oldParent.getRelationCode();
                newRelationCode = parent.getRelationCode();
            }
            sysDeptMapper.updateRelationCode(oldRelationCode, newRelationCode, sysDept.getRelationCode());
        }
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @Override
    public SysDept detailInfo(String id) {
        return sysDeptMapper.selectById(id);
    }

    /**
     * 删除部门
     * @param id
     */
    @Override
    public void deleted(String id) {
        SysDept sysDept = sysDeptMapper.selectById(id);
        if (null == sysDept) {
            log.error("传入 的 id:{}不合法", id);
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("relation_code", sysDept.getRelationCode());
        List<String> deptIds = sysDeptMapper.selectObjs(queryWrapper);
        List<SysUser> list = userService.getUserListByDeptIds(deptIds);
        if (!list.isEmpty()) {
            throw new MyException(BaseResponseCode.NOT_PERMISSION_DELETED_DEPT);
        }
        sysDeptMapper.deleteById(id);
    }

    /**
     * 部门树列表
     * @param deptId
     * @return
     */
    @Override
    public List<DeptRespNodeVO> deptTreeList(String deptId) {
        List<SysDept> list;
        if (StringUtils.isEmpty(deptId)) {
            list = sysDeptMapper.selectList(new QueryWrapper<SysDept>());
        } else {
            SysDept sysDept = sysDeptMapper.selectById(deptId);
            if (sysDept == null) {
                throw new MyException(BaseResponseCode.DATA_ERROR);
            }
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.likeRight("relation_code", sysDept.getRelationCode());
            List<String> childIds = sysDeptMapper.selectObjs(queryWrapper);

            queryWrapper = new QueryWrapper();
            queryWrapper.notIn("id", childIds);
            list = sysDeptMapper.selectList(queryWrapper);
        }
        //默认加一个顶级方便新增顶级部门
        DeptRespNodeVO respNodeVO = new DeptRespNodeVO();
        respNodeVO.setTitle("默认顶级部门");
        respNodeVO.setId("0");
        respNodeVO.setSpread(true);
        respNodeVO.setChildren(getTree(list));
        List<DeptRespNodeVO> result = new ArrayList<>();
        result.add(respNodeVO);
        return result;
    }

    private List<DeptRespNodeVO> getTree(List<SysDept> all) {
        List<DeptRespNodeVO> list = new ArrayList<>();
        for (SysDept sysDept : all) {
            if (sysDept.getPid().equals("0")) {
                DeptRespNodeVO deptTree = new DeptRespNodeVO();
                BeanUtils.copyProperties(sysDept, deptTree);
                deptTree.setTitle(sysDept.getName());
                deptTree.setSpread(true);
                deptTree.setChildren(getChild(sysDept.getId(), all));
                list.add(deptTree);
            }
        }
        return list;
    }

    private List<DeptRespNodeVO> getChild(String id, List<SysDept> all) {
        List<DeptRespNodeVO> list = new ArrayList<>();
        for (SysDept sysDept : all) {
            if (sysDept.getPid().equals(id)) {
                DeptRespNodeVO deptTree = new DeptRespNodeVO();
                BeanUtils.copyProperties(sysDept, deptTree);
                deptTree.setTitle(sysDept.getName());
                deptTree.setChildren(getChild(sysDept.getId(), all));
                list.add(deptTree);
            }
        }
        return list;
    }

    @Override
    public List<SysDept> selectAll() {
        List<SysDept> list = sysDeptMapper.selectList(new QueryWrapper<>());
        for (SysDept sysDept : list) {
            SysDept parent = sysDeptMapper.selectById(sysDept.getPid());
            if (parent != null) {
                sysDept.setPidName(parent.getName());
            }
        }
        return list;
    }
}
