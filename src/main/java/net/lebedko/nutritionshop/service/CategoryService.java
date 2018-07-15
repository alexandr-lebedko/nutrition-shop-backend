package net.lebedko.nutritionshop.service;

import net.lebedko.nutritionshop.dto.CategoryDto;
import net.lebedko.nutritionshop.dto.CategoryRequest;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryRequest categoryRequest);

    CategoryDto update(Long id, CategoryRequest updateCategoryRequest);

    CategoryDto findById(Long id);

    List<CategoryDto> findAll();

    void delete(Long id);
}
