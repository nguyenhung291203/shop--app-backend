package com.example.shopappbackend.service;

import com.example.shopappbackend.dto.ProductDTO;
import com.example.shopappbackend.dto.ProductImageDTO;
import com.example.shopappbackend.model.Product;
import com.example.shopappbackend.model.ProductImage;
import com.example.shopappbackend.response.PageResponse;
import com.example.shopappbackend.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    PageResponse<ProductResponse> getAllProducts(String search, Pageable pageable);

    ProductResponse getProductById(Long id);

    ProductResponse insertProduct(ProductDTO productDTO);

    ProductResponse updateProduct(Long id, ProductDTO productDTO);

    void deleteProductById(Long id);

    ProductImage insertProductImage(Long productId, ProductImageDTO productImageDTO);
    void generateFakeProducts();
}
