package net.lebedko.nutritionshop.category;

import net.lebedko.nutritionshop.dto.CategoryDto;
import net.lebedko.nutritionshop.model.Category;
import net.lebedko.nutritionshop.service.transformer.CategoryTransformer;
import org.junit.Test;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CategoryTransformerTest {
    private CategoryTransformer categoryTransformer = new CategoryTransformer();


    @Test
    public void toDto_setsAllFields() {
        Category category = random(Category.class);

        CategoryDto dto = categoryTransformer.toDto(category);

        assertThat(dto.getId()).isEqualTo(category.getId());
        assertThat(dto.getName()).isEqualTo(category.getName());
    }

    @Test
    public void fromDto_setsAllFields() {
        CategoryDto dto = random(CategoryDto.class);

        Category category = categoryTransformer.fromDto(dto);

        assertThat(category.getId()).isEqualTo(dto.getId());
        assertThat(category.getName()).isEqualTo(dto.getName());
    }
}