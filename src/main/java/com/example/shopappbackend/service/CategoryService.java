package com.example.shopappbackend.service;

import com.example.shopappbackend.dto.CategoryDTO;
import com.example.shopappbackend.model.Category;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    Category insertCategory(CategoryDTO category) throws BadRequestException;
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long id,CategoryDTO category);
    void deleteCategory(long id);
}
