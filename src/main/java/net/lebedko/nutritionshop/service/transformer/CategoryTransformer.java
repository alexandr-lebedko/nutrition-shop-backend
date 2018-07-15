package net.lebedko.nutritionshop.service.transformer;

import net.lebedko.nutritionshop.dto.CategoryDto;
import net.lebedko.nutritionshop.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryTransformer implements Transformer<Category, CategoryDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CategoryDto toDto(Category from) {
        return modelMapper.map(from, CategoryDto.class);
    }

    @Override
    public Category fromDto(CategoryDto from) {
        return modelMapper.map(from, Category.class);
    }
}
