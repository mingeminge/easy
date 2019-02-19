package com.yzm.majoryc.mapper;

import com.yzm.majoryc.DO.order.DailyDO;
import com.yzm.majoryc.DO.order.OrderDO;
import com.yzm.majoryc.DO.order.OrderInfoDO;
import com.yzm.majoryc.DTO.DailyDTO;
import com.yzm.majoryc.DTO.OrderInfoDTO;
import com.yzm.majoryc.VO.order.DailyVO;
import com.yzm.majoryc.VO.order.OrderInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {

    /**
     * @param time
     * @param buyerName
     * @param status
     * @return 订单列表
     */
    List<OrderDO> orderList(@Param("time") String time, @Param("buyerName") String buyerName, @Param("status") Integer status);

    /**
     * @param orderId
     * @return 订单详情
     */
    List<OrderInfoDO> orderInfoList(@Param("orderId") String orderId);

    /**
     * @param orderInfoDTO
     * @return 添加订单
     */
    Integer addOrder(OrderInfoDTO orderInfoDTO);

    /**
     * @param orderInfoList
     * @return 添加订单详情
     */
    Integer addOrderInfo(@Param("orderInfoList") List<OrderInfoVO> orderInfoList);

    /**
     * @param orderId
     * @return 删除订单
     */
    Integer deleteOrder(@Param("orderId") String orderId);

    /**
     * @param orderId
     * @return 删除订单详情
     */
    Integer deleteOrderInfo(@Param("orderId") String orderId);

    /**
     * @param orderInfoDTO
     * @return 修改订单
     */
    Integer updateOrder(OrderInfoDTO orderInfoDTO);

    /**
     * @param orderInfoList
     * @return 修改订单详情
     */
    Integer updateOrderInfo(@Param("orderInfoList") List<OrderInfoVO> orderInfoList);

    /**
     * @param dailyVO
     * @return 添加产量
     */
    Integer addDaily(DailyVO dailyVO);

    /**
     * @param surplus
     * @param id
     * @return 修改订单详情的剩余数量
     */
    Integer updateInfoSurplus(@Param("surplus") Integer surplus, @Param("id") Integer id);

    /**
     * @param id
     * @return 获取订单详情的剩余数量
     */
    Integer getInfoSurplus(@Param("id") Integer id);

    /**
     * @param orderId
     * @param status
     * @return 修改订单状态
     */
    Integer updateOrderStatus(@Param("orderId") String orderId, @Param("status") Integer status);

    /**
     * @param time
     * @param workShift
     * @param treadNum
     * @return 产量列表
     */
    List<DailyDO> dailyList(@Param("time") String time, @Param("workShift") Integer workShift, @Param("treadNum") String treadNum);

    /**
     * @return 班次产量统计
     */
    List<DailyDTO> shiftOutput();

    /**
     * @return 每日产量统计
     */
    List<DailyDTO>outputDay();
}
