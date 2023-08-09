package com.joeun.mapper;


import com.joeun.dto.OrderDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    void saveOrder(OrderDto orderDto);

    List<OrderDto> getAllOrders();

    OrderDto findOrderById(@Param("productId") int productId,@Param("userId")  int userId);

}