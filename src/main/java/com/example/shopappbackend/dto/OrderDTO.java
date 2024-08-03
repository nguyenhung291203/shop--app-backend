package com.example.shopappbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @JsonProperty("fullname")
    @NotBlank(message = "Tên khách hàng không được để trống")
    private String fullName;
    private String email;
    @JsonProperty("phone_number")
    @NotBlank(message = "Số điện thoại là bắt buộc")
    @Size(min = 10,max = 10,message = "Sai định dạng số định dạng số điện thoại")
    private String phoneNumber;
    @NotBlank(message = "Địa chỉ là bắt buộc")
    private String address;
    private String note;
    @JsonProperty("order_date")
    private Date orderDate;
    private String status;
    @JsonProperty("total_money")
    private float totalMoney;
    @JsonProperty("shipping_method")
    private String shippingMethod;
    @JsonProperty("shipping_address")
    private String shippingAddress;
    @JsonProperty("shipping_date")
    private Date shippingDate;
    @JsonProperty("tracking_number")
    private String trackingNumber;
    private boolean active;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("user_id")
    @Min(value = 0, message = "Tài khoản không hợp lệ")
    private Long userId;
}
