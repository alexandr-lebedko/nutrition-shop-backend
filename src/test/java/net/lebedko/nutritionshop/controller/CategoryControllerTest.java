package net.lebedko.nutritionshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.lebedko.nutritionshop.dto.CategoryDto;
import net.lebedko.nutritionshop.dto.CategoryRequest;
import net.lebedko.nutritionshop.service.CategoryService;
import net.lebedko.nutritionshop.service.exception.NoSuchCategoryException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryService categoryService;

    @Test
    public void create() throws Exception {
        CategoryDto categoryDto = random(CategoryDto.class);
        doReturn(categoryDto).when(categoryService).save(any(CategoryRequest.class));

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName(categoryDto.getName());

        mockMvc.perform(
                post("/categories")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(categoryDto.getId())))
                .andExpect(jsonPath("$.name", is(categoryRequest.getName())));

        verify(categoryService, times(1)).save(any(CategoryRequest.class));
    }

    @Test
    public void update_whenCategoryExists() throws Exception {
        CategoryDto categoryDto = random(CategoryDto.class);

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName(categoryDto.getName());

        doReturn(categoryDto).when(categoryService).update(1L, categoryRequest);


        mockMvc.perform(
                put("/categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(categoryDto.getId())))
                .andExpect(jsonPath("$.name", is(categoryRequest.getName())));

        verify(categoryService, times(1)).update(1L, categoryRequest);
    }

    @Test
    public void update_whenCategoryAbsent() throws Exception {
        CategoryRequest categoryRequest = random(CategoryRequest.class);

        doThrow(NoSuchCategoryException.class).when(categoryService).update(1L, categoryRequest);

        mockMvc.perform(
                put("/categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).update(1L, categoryRequest);
    }
}