package com.example.shopappbackend.mapper;

import com.example.shopappbackend.dto.OrderDetailDTO;
import com.example.shopappbackend.model.Order;
import com.example.shopappbackend.model.OrderDetail;
import com.example.shopappbackend.model.Product;
import com.example.shopappbackend.response.OrderDetailResponse;

public class OrderDetailMapping {
    public static OrderDetail mapOrderDetailDTOToOrderDetail(OrderDetailDTO orderDetailDTO, Product product, Order order) {
        return OrderDetail.builder()
                .color(orderDetailDTO.getColor())
                .totalMoney(order.getTotalMoney())
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .price(orderDetailDTO.getPrice())
                .product(product)
                .order(order)
                .build();
    }
}
