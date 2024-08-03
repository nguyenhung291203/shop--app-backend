package com.example.shopappbackend.controller;

import com.example.shopappbackend.dto.ProductDTO;
import com.example.shopappbackend.dto.ProductImageDTO;
import com.example.shopappbackend.model.ProductImage;
import com.example.shopappbackend.service.ProductService;
import com.example.shopappbackend.utils.FileServiceUtil;
import com.example.shopappbackend.utils.ParamUtil;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/products")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileServiceUtil fileServiceUtil;

    @GetMapping("")
    public ResponseEntity<?> getAllProducts(@RequestParam Map<String, Object> params) {
        String search = ParamUtil.getSearchParam(params);
        Pageable pageable = ParamUtil.getPageable(params);
        return ResponseEntity.ok(productService.getAllProducts(search, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> insertProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.insertProduct(productDTO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/upload-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertProductImage(@Valid @PathVariable Long id,
                                                @ModelAttribute("files") List<MultipartFile> files) {
        try {
            files = files.isEmpty() ? new ArrayList<MultipartFile>() : files;
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String validationError = fileServiceUtil.validateFile(file);
                    if (validationError != null) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
                    }
                    String fileName = fileServiceUtil.storeFile(file);

                    ProductImage productImage = productService.insertProductImage(id,
                            ProductImageDTO.builder()
                                    .productId(id)
                                    .imageUrl(fileName).build());
                    productImages.add(productImage);
                }
            }
            return new ResponseEntity<>(productImages, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error storing file");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @PathVariable long id, @Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@Valid @PathVariable long id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PostMapping("/generateFakeProducts")
    private ResponseEntity<?> generateFakeProducts(){
        productService.generateFakeProducts();
        return ResponseEntity.ok("Success");
    }
}
