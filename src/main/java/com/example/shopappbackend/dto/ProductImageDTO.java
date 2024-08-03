package com.example.shopappbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageDTO {
    @JsonProperty("image_url")
    @Size(min = 5, max = 300, message = "Độ dài của đường dẫn chưa hợp lệ")
    private String imageUrl;
    @JsonProperty("product_id")
    @Min(value = 1, message = "ID của sản phẩm không hợp lệ")
    private Long productId;
}
