package com.example.shopappbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @NotNull(message = "Id giỏ hàng không được phép để trống")
    private Long orderId;
    @JsonProperty("product_id")
    @NotNull(message = "Id sản phẩm không được phép để trống")
    private Long productId;
    private Float price;
    @JsonProperty("number_of_products")
    @Min(value = 0, message = "Số lượng sản phẩm phải trên 0")
    private int numberOfProducts;
    private String color;
    @JsonProperty("total_money")
    private float totalMoney;
}
