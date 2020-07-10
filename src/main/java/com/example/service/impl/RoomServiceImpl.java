package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.*;
import com.example.entity.hotel.RoomType;
import com.example.exception.MyException;
import com.example.exception.code.BaseResponseCode;
import com.example.mapper.RoomInfoMapper;
import com.example.mapper.RoomTypeMapper;
import com.example.service.RedisService;
import com.example.service.RoomService;
import com.example.vo.response.RoomRespNodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ClassName: RoomServiceImpl
 * @Description: RoomServiceImpl
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Service
@Slf4j
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private RoomTypeMapper roomTypeMapper;

    /**
     *  新增房间
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoomInfo addRoom(RoomInfo vo) {
        RoomInfo roomInfo = new RoomInfo();
        //Map<String, Object> map = new HashMap<>();
        vo.setCreateTime(new Date());
        String rtName = vo.getRtName();
        if(rtName!=null){
            //map.put("rtName", rtName);
            QueryWrapper<RoomType> queryWrapper = new QueryWrapper<>();
            List<RoomType> list = roomTypeMapper.selectList(queryWrapper);
            for (RoomType r : list) {
                String rtId = r.getRtId();
                RoomType roomType = roomTypeMapper.selectById(rtId);
                String rtName1 = "";
                if((rtName1=roomType.getRtName()).equals(rtName)){
                    vo.setRtId(rtId);
                }
            }
        }
        BeanUtils.copyProperties(vo, roomInfo);  // 对象中的属性值互拷
        roomInfo.setRReg(new Date());

        int count = roomInfoMapper.insert(roomInfo);
        if (count != 1){
            throw new MyException(BaseResponseCode.OPERATION_ERRO);  // 操作失败
        }
        return roomInfo;
    }

    /**
     *  更新房间
     * @param vo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRoom(RoomInfo vo) {
        String rtName = vo.getRtName();
        String rtId = "";
        if(rtName == null) {
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }else{
            List<RoomType> roomTypes = roomTypeMapper.selectList(new QueryWrapper<>());
            for (RoomType rt : roomTypes) {
                if ((rt.getRtName()).equals(rtName)){
                    rtId = rt.getRtId();
                }
            }
        }
        vo.setRtId(rtId);
        RoomInfo roomInfo = roomInfoMapper.selectById(vo.getRId());
        if (null == roomInfo){
            log.error("传入 的 id:{}不合法", vo.getRId());
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        //System.out.println("[RoomServiceImpl]updateRoom(104): " + roomInfo);
        RoomInfo update = new RoomInfo();
        BeanUtils.copyProperties(vo, update);
        //System.out.println("[RoomServiceImpl]updateRoom(108): " + roomInfo);
        update.setUpdateTime(new Date());
        int count = roomInfoMapper.updateById(update);
        if (count != 1) {
            throw new MyException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    /**
     *  房间详情
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoomInfo detailInfo(String id) {
        RoomInfo roominfo = roomInfoMapper.selectById(id);
        if (roominfo == null) {
            log.error("传入 的 id:{}不合法", id);
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        return roominfo;
    }

    /**
     *  删除房间
     * @param roomIds
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletedRoom(String roomIds) {
        QueryWrapper<RoomInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("r_id", roomIds);
        roomInfoMapper.delete(queryWrapper);
    }

    /**
     * 根据房型id获取房间列表
     * @param typeIds
     * @return
     */
    @Override
    public List<RoomInfo> getRoomListByType(List<String> typeIds) {
        QueryWrapper<RoomInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("rt_id", typeIds);
        return roomInfoMapper.selectList(queryWrapper);
    }

    @Override
    public List<RoomInfo> selectAll() {
        return roomInfoMapper.selectList(null);
    }

    /**
     * 房型选择树
     * @param roomId
     * @return
     */
    @Override
    public List<RoomRespNodeVO> roomTreeList(String roomId) {
        List<RoomType> list;
        if(StringUtils.isEmpty(roomId)){
            list = roomTypeMapper.selectList(new QueryWrapper<>());
        }else {
            RoomInfo roomInfo = roomInfoMapper.selectById(roomId);
            if (roomInfo == null){
                throw new MyException(BaseResponseCode.DATA_ERROR);  // 传入数据错误
            }
            String rtId = roomInfo.getRtId();
            if(StringUtils.isEmpty(rtId)){
                list = roomTypeMapper.selectList(new QueryWrapper<>());
            }else {
                RoomType roomType = roomTypeMapper.selectById(rtId);
                if (roomType == null){
                    throw new MyException(BaseResponseCode.DATA_ERROR);
                }
                QueryWrapper<RoomType> queryWrapper = new QueryWrapper<>();
                list = roomTypeMapper.selectList(queryWrapper);
            }
        }
        //System.out.println("[RoomServiceImpl]roomTreeList(192): " + list);
        RoomRespNodeVO respNodeVO = new RoomRespNodeVO();
        respNodeVO.setTitle("默认顶级部门");
        respNodeVO.setId("0");
        respNodeVO.setSpread(true);
        respNodeVO.setChildren(getTree(list));
        List<RoomRespNodeVO> result = new ArrayList<>();
        result.add(respNodeVO);
        return result;
    }

    private List<RoomRespNodeVO> getTree(List<RoomType> all) {
        List<RoomRespNodeVO> list = new ArrayList<>();
        //System.out.println("[RoomServiceImpl]getTree(249): " + all);
        for (RoomType roomType : all) {
            RoomRespNodeVO roomTree = new RoomRespNodeVO();
            BeanUtils.copyProperties(roomType, roomTree);
            roomTree.setTitle(roomType.getRtName());
            roomTree.setId(roomType.getRtId());
            roomTree.setSpread(true);
            roomTree.setChildren(null);
            list.add(roomTree);
        }
        //System.out.println("[RoomServiceImpl]getTree(228): " + list);
        return list;
    }

    /**
     *  获取分页
     * @param vo
     * @return
     */
    @Override
    public IPage<RoomInfo> pageInfo(RoomInfo vo) {
        //System.out.println("[RoomServiceImpl]roomTreeList(254): " + vo);
        Page<RoomInfo> page = new Page<>(vo.getPage(), vo.getLimit());
        QueryWrapper<RoomInfo> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(vo.getRNum())) {
            queryWrapper.like("r_num", vo.getRNum());  // 比较值是否相等
        }
        if (!StringUtils.isEmpty(vo.getRtName())) {
            queryWrapper.like("rt_name", vo.getRtName());  // 比较值是否相等
        }
        if (!StringUtils.isEmpty(vo.getRState())) {
            queryWrapper.like("r_state", vo.getRState());  // 比较值是否相等
        }
//        if (!StringUtils.isEmpty(vo.getRReg())) {
//            queryWrapper.gt("r_reg", vo.getStartTime());
//        }
//        if (!StringUtils.isEmpty(vo.getRReg()) ) {
//            queryWrapper.lt("r_reg", vo.getEndTime());
//        }
        queryWrapper.orderByDesc("r_num");
        IPage<RoomInfo> iPage = roomInfoMapper.selectPage(page, queryWrapper);
        //System.out.println("[RoomServiceImpl]roomTreeList(268): " + queryWrapper);
        //System.out.println("[RoomServiceImpl]roomTreeList(269): " + iPage.getRecords().isEmpty());
        //System.out.println("[RoomServiceImpl]roomTreeList(270): " + iPage.getRecords());
        if(!iPage.getRecords().isEmpty()){
            for (RoomInfo roomInfo: iPage.getRecords()) {
                RoomType roomType = roomTypeMapper.selectById(roomInfo.getRtId());
                if (roomType!=null){
                    //System.out.println("" + roomType);
                    roomInfo.setRtName(roomType.getRtName());
                }
            }
        }
        return iPage;
    }



}
