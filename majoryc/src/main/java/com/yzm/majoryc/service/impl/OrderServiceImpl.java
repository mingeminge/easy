package com.yzm.majoryc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzm.majoryc.DO.order.DailyDO;
import com.yzm.majoryc.DO.order.OrderDO;
import com.yzm.majoryc.DO.order.OrderInfoDO;
import com.yzm.majoryc.DTO.DailyDTO;
import com.yzm.majoryc.DTO.OrderInfoDTO;
import com.yzm.majoryc.VO.TableInfo;
import com.yzm.majoryc.VO.order.DailyVO;
import com.yzm.majoryc.VO.order.OrderInfoVO;
import com.yzm.majoryc.mapper.OrderMapper;
import com.yzm.majoryc.service.OrderService;
import com.yzm.majoryc.utils.OrderIdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public TableInfo<List<OrderInfoDTO>> orderList(Integer page, Integer limit, String time, String buyerName, Integer status) {
        PageHelper.startPage(page, limit);
        List<OrderDO> orderDOS = orderMapper.orderList(time, buyerName, status);
        PageInfo<OrderDO> info = new PageInfo<>(orderDOS);
        List<OrderInfoDTO> orderInfoDTOList = new ArrayList<>();
        orderDOS.forEach(item -> {//通过orderid查询订单详情
            OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
            BeanUtils.copyProperties(item, orderInfoDTO);
            //订单详情
            List<OrderInfoDO> orderInfoDOS = orderMapper.orderInfoList(item.getOrderId());
            List<OrderInfoVO> orderInfoVOList = new ArrayList<>();
            orderInfoDOS.forEach(orderInfoDO -> {
                OrderInfoVO orderInfoVO = new OrderInfoVO();
                BeanUtils.copyProperties(orderInfoDO, orderInfoVO);
                orderInfoVOList.add(orderInfoVO);
            });
            orderInfoDTO.setOrderInfo(orderInfoVOList);
            orderInfoDTOList.add(orderInfoDTO);
        });
        TableInfo<List<OrderInfoDTO>> tableInfo = new TableInfo<>();
        tableInfo.setData(orderInfoDTOList);
        tableInfo.setCount(info.getTotal());
        return tableInfo;
    }

    @Override
    @Transactional
    public Integer addOrder(OrderInfoDTO orderInfoDTO) {
        String orderId = OrderIdUtil.getOrderId();
        Date date = new Date();
        orderInfoDTO.setOrderId(orderId);
        orderInfoDTO.setCreateTime(date);
        orderInfoDTO.setUpdateTime(date);
        orderMapper.addOrder(orderInfoDTO);
        orderInfoDTO.getOrderInfo().forEach(item -> {
            item.setOrderId(orderId);
        });
        return orderMapper.addOrderInfo(orderInfoDTO.getOrderInfo());
    }

    @Override
    @Transactional
    public Integer deleteOrder(String orderId) {
        orderMapper.deleteOrder(orderId);
        return orderMapper.deleteOrderInfo(orderId);
    }

    @Override
    @Transactional
    public Integer updateOrder(OrderInfoDTO orderInfoDTO) {
        Date date = new Date();
        orderInfoDTO.setUpdateTime(date);
        orderMapper.updateOrder(orderInfoDTO);
        orderInfoDTO.getOrderInfo().forEach(item -> {
            item.setOrderId(orderInfoDTO.getOrderId());
        });
        return orderMapper.updateOrderInfo(orderInfoDTO.getOrderInfo());
    }

    @Override
    @Transactional
    public Integer addDaily(DailyVO dailyVO) {
        synchronized (this) {
            Integer infoSurplus = orderMapper.getInfoSurplus(dailyVO.getId());//获取数据库中的剩余数量
            Integer surplus = infoSurplus - dailyVO.getOutput();//得到剩余数量
            orderMapper.updateInfoSurplus(surplus, dailyVO.getId());//将剩余数量更新到数据库
            dailyVO.setSurplus(surplus);
            Date date = new Date();
            dailyVO.setTime(date);
            dailyVO.setInfoId(dailyVO.getId());
            dailyVO.setId(null);
            Integer integer = orderMapper.addDaily(dailyVO);//将产量插入数据库

            List<OrderInfoDO> orderInfoDOS = orderMapper.orderInfoList(dailyVO.getOrderId());//查询订单下的所有子订单
            Integer sum = 0;
            for (OrderInfoDO orderInfoDO : orderInfoDOS) {
                sum += orderInfoDO.getSurplus();
            }
            if (sum == 0) {//如果所以子订单的剩余数量为0
                orderMapper.updateOrderStatus(dailyVO.getOrderId(), 2);//将订单状态设置为完成
            }
            return integer;
        }

    }

    @Override
    public TableInfo<List<DailyVO>> dailyList(Integer page, Integer limit, String time, Integer workShift, String treadNum) {
        PageHelper.startPage(page, limit);
        List<DailyDO> dailyDOS = orderMapper.dailyList(time, workShift, treadNum);

        List<DailyVO> dailyVOS = new ArrayList<>();
        dailyDOS.forEach(daily -> {
            DailyVO dailyVO = new DailyVO();
            BeanUtils.copyProperties(daily, dailyVO);
            dailyVOS.add(dailyVO);
        });
        PageInfo<DailyDO> pageInfo = new PageInfo<>(dailyDOS);
        TableInfo<List<DailyVO>> tableInfo = new TableInfo<>();
        tableInfo.setCount(pageInfo.getTotal());
        tableInfo.setData(dailyVOS);
        return tableInfo;
    }

    @Override
    public List<DailyDTO> shiftOutput() {
        return orderMapper.shiftOutput();
    }

    @Override
    public List<DailyDTO> outputDay() {
        return orderMapper.outputDay();
    }
}
