package com.example.shopappbackend.service.impl;


import com.example.shopappbackend.dto.OrderDTO;
import com.example.shopappbackend.exception.NotFoundException;
import com.example.shopappbackend.model.Order;
import com.example.shopappbackend.model.OrderStatus;
import com.example.shopappbackend.model.User;
import com.example.shopappbackend.repository.OrderRepository;
import com.example.shopappbackend.repository.UserRepository;
import com.example.shopappbackend.response.OrderResponse;
import com.example.shopappbackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public OrderResponse insertOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tài khoản có id: " + orderDTO.getUserId()));

        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(new Date());
        Date shippingDate = orderDTO.getShippingDate();
        if (shippingDate == null) {
            shippingDate = new Date();
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);

        return modelMapper.map(order, OrderResponse.class);
    }


    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng có id: " + id));
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tài khoản có id: " + orderDTO.getUserId()));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(user);

        return modelMapper.map(orderRepository.save(order), OrderResponse.class);
    }

    @Override
    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng có id: " + id));
        order.setActive(false);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream().map(order
                        -> modelMapper.map(order, OrderResponse.class)).toList();

    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng có id: " + id));
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tài khoản có id: " + userId));

        return orderRepository.findAllByUser(user)
                .stream().map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }
}
