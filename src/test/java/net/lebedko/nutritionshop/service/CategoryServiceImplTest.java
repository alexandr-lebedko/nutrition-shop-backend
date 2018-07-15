package net.lebedko.nutritionshop.service;

import net.lebedko.nutritionshop.dto.CategoryDto;
import net.lebedko.nutritionshop.dto.CategoryRequest;
import net.lebedko.nutritionshop.model.Category;
import net.lebedko.nutritionshop.repository.CategoryRepository;
import net.lebedko.nutritionshop.service.exception.NoSuchCategoryException;
import net.lebedko.nutritionshop.service.transformer.CategoryTransformer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomCollectionOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {
    private static final Long ID = 1L;

    @Mock
    private CategoryRepository repository;
    @Spy
    private CategoryTransformer transformer;
    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;

    @InjectMocks
    private CategoryServiceImpl subject;

    @Test
    public void save() {
        CategoryRequest createRequest = random(CategoryRequest.class);

        Category category = new Category();
        category.setName(createRequest.getName());

        doReturn(category).when(repository).save(any(Category.class));

        subject.save(createRequest);

        verify(transformer, times(1)).toDto(category);
        verify(repository, times(1)).save(categoryArgumentCaptor.capture());

        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertThat(capturedCategory.getName()).isEqualTo(createRequest.getName());
    }

    @Test(expected = NoSuchCategoryException.class)
    public void update_whenCategoryAbsent() {
        doReturn(false).when(repository).existsById(ID);

        CategoryRequest request = random(CategoryRequest.class);

        subject.update(ID, request);
    }

    @Test
    public void update_whenCategoryPresent() {
        doReturn(true).when(repository).existsById(ID);

        CategoryRequest categoryRequest = random(CategoryRequest.class);

        Category category = new Category();
        category.setId(ID);
        category.setName(categoryRequest.getName());

        doAnswer(returnsFirstArg()).when(repository).save(any(Category.class));

        subject.update(ID, categoryRequest);

        verify(repository, times(1)).save(categoryArgumentCaptor.capture());
        verify(transformer, times(1)).toDto(category);

        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertThat(capturedCategory.getId()).isEqualTo(ID);
        assertThat(capturedCategory.getName()).isEqualTo(categoryRequest.getName());
    }

    @Test(expected = NoSuchCategoryException.class)
    public void findById_whenCategoryAbsent() {
        doReturn(Optional.empty()).when(repository).findById(ID);

        subject.findById(ID);
    }

    @Test
    public void findById_whenCategoryPresent() {
        Category category = random(Category.class);

        doReturn(Optional.of(category)).when(repository).findById(anyLong());

        CategoryDto actual = subject.findById(ID);

        verify(repository, times(1)).findById(ID);
        verify(transformer, times(1)).toDto(category);

        assertThat(actual.getId()).isEqualTo(category.getId());
        assertThat(actual.getName()).isEqualTo(category.getName());
    }

    @Test
    public void findAll() {
        doReturn(randomCollectionOf(3, Category.class)).when(repository).findAll();

        List<CategoryDto> actual = subject.findAll();

        verify(repository, times(1)).findAll();
        verify(transformer, times(3)).toDto(any(Category.class));

        assertThat(actual.size()).isEqualTo(3);
    }

    @Test
    public void delete() {
        subject.delete(ID);

        verify(repository).deleteById(ID);
    }
}