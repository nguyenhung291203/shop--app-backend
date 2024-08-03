package com.example.shopappbackend.service.impl;

import com.example.shopappbackend.dto.ProductDTO;
import com.example.shopappbackend.dto.ProductImageDTO;
import com.example.shopappbackend.exception.BadRequestException;
import com.example.shopappbackend.exception.NotFoundException;
import com.example.shopappbackend.mapper.ProductMapping;
import com.example.shopappbackend.model.Category;
import com.example.shopappbackend.model.Product;
import com.example.shopappbackend.model.ProductImage;
import com.example.shopappbackend.repository.CategoryRepository;
import com.example.shopappbackend.repository.ProductImageRepository;
import com.example.shopappbackend.repository.ProductRepository;
import com.example.shopappbackend.response.PageResponse;
import com.example.shopappbackend.response.ProductResponse;
import com.example.shopappbackend.service.ProductService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public PageResponse<ProductResponse> getAllProducts(String search, Pageable pageable) {
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(search, pageable);

        List<ProductResponse> productResponses = productPage.getContent().stream()
                .map(ProductMapping::mapProductToProductResponse)
                .collect(Collectors.toList());
        if (productResponses.isEmpty())
            throw new NotFoundException("Không tìm thấy sản phẩm nào");

        return PageResponse.<ProductResponse>builder()
                .contents(productResponses)
                .numberOfElements(productPage.getNumberOfElements())
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .build();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Sản phẩm có id: " + id + " không tồn tại"));
        return ProductMapping.mapProductToProductResponse(product);
    }

    @Override
    public ProductResponse insertProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()
                        -> new NotFoundException("Thể loại có id: "
                        + productDTO.getCategoryId() + " không tồn tại"));
        Product product = Product.builder()
                .name(productDTO.getName())
                .thumbnail(productDTO.getDescription())
                .description(productDTO.getDescription())
                .category(category)
                .price(productDTO.getPrice())
                .build();

        return ProductMapping.mapProductToProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()
                        -> new NotFoundException("Thể loại có id: "
                        + productDTO.getCategoryId() + " không tồn tại"));
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Sản phẩm có id: " + id + " không tồn tại"));
        product.setCategory(category);
        product.setDescription(productDTO.getDescription());
        product.setThumbnail(productDTO.getThumbnail());
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());
        return ProductMapping.mapProductToProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteProductById(Long id) {
        if (this.productRepository.findAll().isEmpty())
            throw new BadRequestException("Hiện tại không có sản phẩm nào");
        if (!this.productRepository.existsById(id))
            throw new BadRequestException("Sản phẩm có id: " + id + " không tồn tại");
        this.productRepository.deleteById(id);
    }

    @Override
    public ProductImage insertProductImage(Long productId, ProductImageDTO productImageDTO) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Sản phẩm có id: " + productId + " không tồn tại"));
        List<ProductImage> productImages = productImageRepository.findAllByProductId(productId);
        if (productImages.size() >= 5)
            throw new BadRequestException("Vượt quá kích thước cho phép");
        ProductImage productImage = ProductImage.builder()
                .imageUrl(productImageDTO.getImageUrl())
                .product(product)
                .build();
        return productImageRepository.save(productImage);
    }

    @Override
    public void generateFakeProducts() {
        Faker faker = new Faker();
        for (int i = 0; i < 500; i++) {
            String productName = faker.commerce().productName();
            if (productRepository.existsByName(productName))
                continue;
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price(faker.number().numberBetween(10, 1000))
                    .description(faker.lorem().sentence())
                    .categoryId((long) faker.number().numberBetween(2, 5))
                    .build();
            insertProduct(productDTO);
        }
    }
}
