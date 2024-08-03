package com.example.shopappbackend.service.impl;

import com.example.shopappbackend.dto.CategoryDTO;
import com.example.shopappbackend.exception.BadRequestException;
import com.example.shopappbackend.exception.DataIntegrityViolationException;
import com.example.shopappbackend.exception.NotFoundException;
import com.example.shopappbackend.model.Category;
import com.example.shopappbackend.repository.CategoryRepository;
import com.example.shopappbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category insertCategory(CategoryDTO category) throws BadRequestException {
        if (categoryRepository.existsByName(category.getName()))
            throw new DataIntegrityViolationException("Tên thể loại đã tồn tại");
        Category categoryNew = Category.builder().name(category.getName()).build();
        return this.categoryRepository.save(categoryNew);
    }

    @Override
    public Category getCategoryById(long id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy thể loại có id: " + id));
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        if (categories.isEmpty())
            throw new NotFoundException("Không tìm thấy bất kì thể loại nào");
        return categories;
    }

    @Override
    public Category updateCategory(long id, CategoryDTO category) {
        Category categoryNew = this.getCategoryById(id);
        categoryNew.setName(category.getName());
        return categoryNew;
    }

    @Override
    public void deleteCategory(long id) {
        if (this.categoryRepository.findAll().isEmpty())
            throw new BadRequestException("Hiện tại không còn thể loại nào nữa");
        if (this.categoryRepository.findById(id).isEmpty())
            throw new NotFoundException("Không thấy thể loại có id: " + id);
        this.categoryRepository.deleteById(id);
    }
}
