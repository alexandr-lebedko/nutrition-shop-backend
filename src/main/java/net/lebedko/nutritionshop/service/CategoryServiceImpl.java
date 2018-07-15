package net.lebedko.nutritionshop.service;

import lombok.RequiredArgsConstructor;
import net.lebedko.nutritionshop.dto.CategoryDto;
import net.lebedko.nutritionshop.dto.CategoryRequest;
import net.lebedko.nutritionshop.model.Category;
import net.lebedko.nutritionshop.repository.CategoryRepository;
import net.lebedko.nutritionshop.service.exception.NoSuchCategoryException;
import net.lebedko.nutritionshop.service.transformer.CategoryTransformer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryTransformer transformer;

    @Override
    public CategoryDto save(CategoryRequest categoryRequest) {
        Category category = repository.save(toCategory(categoryRequest));

        return transformer.toDto(category);
    }

    @Override
    public CategoryDto update(Long id, CategoryRequest updateCategoryRequest) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new NoSuchCategoryException(id));

        category.setName(updateCategoryRequest.getName());
        repository.save(category);

        return transformer.toDto(category);
    }

    @Override
    public CategoryDto findById(Long id) {
        return repository.findById(id)
                .map(transformer::toDto)
                .orElseThrow(() -> new NoSuchCategoryException(id));
    }

    @Override
    public List<CategoryDto> findAll() {
        return repository.findAll()
                .stream()
                .map(transformer::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private Category toCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());

        return category;
    }
}
