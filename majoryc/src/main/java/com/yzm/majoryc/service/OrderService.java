package com.yzm.majoryc.service;

import com.yzm.majoryc.DTO.DailyDTO;
import com.yzm.majoryc.DTO.OrderInfoDTO;
import com.yzm.majoryc.VO.TableInfo;
import com.yzm.majoryc.VO.order.DailyVO;

import java.util.List;

/**
 * 订单
 */
public interface OrderService {
    /**
     * @param page
     * @param limit
     * @param time
     * @param buyerName
     * @param status
     * @return 订单列表
     */
    TableInfo<List<OrderInfoDTO>> orderList(Integer page, Integer limit, String time, String buyerName, Integer status);

    /**
     * @param orderInfoDTO
     * @return 添加订单
     */
    Integer addOrder(OrderInfoDTO orderInfoDTO);

    /**
     * @param orderId
     * @return 删除订单
     */
    Integer deleteOrder(String orderId);

    /**
     * @param orderInfoDTO
     * @return 修改订单
     */
    Integer updateOrder(OrderInfoDTO orderInfoDTO);

    /**
     * @param dailyVO
     * @return 添加产量
     */
    Integer addDaily(DailyVO dailyVO);

    /**
     * @param page
     * @param limit
     * @param time
     * @param workShift
     * @param treadNum
     * @return 产量列表
     */
    TableInfo<List<DailyVO>> dailyList(Integer page, Integer limit, String time, Integer workShift, String treadNum);

    /**
     * @return 班次产量统计
     */
    List<DailyDTO> shiftOutput();

    /**
     * @return 每日产量统计
     */
    List<DailyDTO>outputDay();
}
