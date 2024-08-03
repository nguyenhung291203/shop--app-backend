package com.example.shopappbackend.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse extends BaseResponse{
    private Long id;
    private String name;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id")
    private long categoryId;
    private float price;
}
