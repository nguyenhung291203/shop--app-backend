package com.example.shopappbackend.service;

import com.example.shopappbackend.dto.OrderDTO;
import com.example.shopappbackend.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse insertOrder(OrderDTO orderDTO);

    OrderResponse updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrderById(Long id);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getOrdersByUserId(Long userId);
}
