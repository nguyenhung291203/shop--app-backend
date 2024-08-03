package com.example.shopappbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    @NotBlank(message = "Tiêu đề là bắt buộc")
    @Size(min = 3, max = 200, message = "Tiêu đề chỉ có 3 đến 200 kí tự")
    private String name;
    @Min(value = 0, message = "Giá sản phẩm phải lớn hơn 0")
    @Max(value = 10000, message = "Giá của sản phẩm không được lớn hơn 100")
    private float price;
    private String description;
    @JsonProperty("category_id")
    @NotNull(message = "ID của thể loại là bắt buộc")
    private Long categoryId;
    private String thumbnail;
}
