package com.joeun.service;

import com.joeun.dto.OrderDto;
import com.joeun.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public void placeOrder(OrderDto orderDto) {
        OrderDto orders = new OrderDto();
        orders.setUserId(orderDto.getUserId());
        orders.setProductId(orderDto.getProductId());
        orders.setOrderDate(new Date());

        // OrderDto를 사용하여 Mapper를 통해 데이터베이스에 주문 정보 저장
        orderMapper.saveOrder(orders);
    }


    public List<OrderDto> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    // 주문 출력
    public List<OrderDto> getOrdersWithProductInfoByUserId(int userId) {
        return orderMapper.getOrdersWithProductInfoByUserId(userId);
    }
}



