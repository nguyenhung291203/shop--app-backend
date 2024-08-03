package com.example.shopappbackend.service.impl;

import com.example.shopappbackend.dto.OrderDetailDTO;
import com.example.shopappbackend.exception.NotFoundException;
import com.example.shopappbackend.mapper.OrderDetailMapping;
import com.example.shopappbackend.model.Order;
import com.example.shopappbackend.model.OrderDetail;
import com.example.shopappbackend.model.Product;
import com.example.shopappbackend.repository.OrderDetailRepository;
import com.example.shopappbackend.repository.OrderRepository;
import com.example.shopappbackend.repository.ProductRepository;
import com.example.shopappbackend.response.OrderDetailResponse;
import com.example.shopappbackend.service.OrderDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderDetailResponse> getAllOrderDetails() {
        return this.orderDetailRepository.findAll()
                .stream().map(orderDetail -> modelMapper.map(orderDetail, OrderDetailResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailResponse getOrderDetailById(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(() -> new NotFoundException("Không thấy order detail có id: " + id));

        return modelMapper.map(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public OrderDetailResponse updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy chi tiết sản phẩm có id: " + id));
        Product product = productRepository.findById(orderDetailDTO
                .getProductId()).orElseThrow(() -> new NotFoundException("Sản phẩm có id: "
                + orderDetailDTO.getProductId() + " không tồn tại"));
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new NotFoundException("Order có id: "
                        + orderDetailDTO.getOrderId() + " không tồn tại"));
        orderDetail.setOrder(order);
        orderDetail.setColor(orderDetailDTO.getColor());
        orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        orderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setProduct(product);

        return modelMapper.map(orderDetailRepository.save(orderDetail)
                , OrderDetailResponse.class);
    }

    @Override
    public OrderDetailResponse insertOrderDetail(OrderDetailDTO orderDetailDTO) {
        Product product = productRepository.findById(orderDetailDTO
                .getProductId()).orElseThrow(() -> new NotFoundException("Sản phẩm có id: "
                + orderDetailDTO.getProductId() + " không tồn tại"));
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new NotFoundException("Order có id: "
                        + orderDetailDTO.getOrderId() + " không tồn tại"));
        OrderDetail orderDetail = OrderDetailMapping.mapOrderDetailDTOToOrderDetail(orderDetailDTO, product, order);
        return modelMapper.map(orderDetailRepository.save(orderDetail), OrderDetailResponse.class);

    }

    @Override
    public List<OrderDetailResponse> getOrderDetailByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order có id: "
                        + orderId + " không tồn tại"));

        return this.orderDetailRepository.findAllByOrder(order)
                .stream().map(orderDetail -> modelMapper.map(orderDetail, OrderDetailResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderDetailById(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy chi tiết sản phẩm có id: " + id));
        orderDetailRepository.delete(orderDetail);
    }
}
