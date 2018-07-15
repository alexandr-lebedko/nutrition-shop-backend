package net.lebedko.nutritionshop.controller;

import lombok.RequiredArgsConstructor;
import net.lebedko.nutritionshop.dto.CategoryDto;
import net.lebedko.nutritionshop.dto.CategoryRequest;
import net.lebedko.nutritionshop.service.CategoryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CategoryDto create(@Valid @RequestBody CategoryRequest category) {
        return categoryService.save(category);
    }

    @PutMapping("{id}")
    public CategoryDto update(@PathVariable Long id, @Valid @RequestBody CategoryRequest category) {
        return categoryService.update(id, category);
    }
}
