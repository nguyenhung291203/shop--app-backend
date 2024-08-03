package com.example.shopappbackend.mapper;

import com.example.shopappbackend.model.Product;
import com.example.shopappbackend.response.ProductResponse;

public class ProductMapping {
    public static ProductResponse mapProductToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .categoryId(product.getCategory().getId())
                .description(product.getDescription())
                .thumbnail(product.getThumbnail())
                .price(product.getPrice())
                .name(product.getName())
                .id(product.getId())
                .build();
        productResponse.setCreatedDate(product.getCreatedDate());
        productResponse.setUpdatedDate(product.getUpdatedDate());
        return productResponse;
    }
}
