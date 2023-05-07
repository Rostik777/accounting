package com.cydeo.service;

import com.cydeo.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO findCategoryById(Long categoryId);
    List<CategoryDTO> getAllCategories() throws Exception;
    boolean hasProduct(Long categoryId);
    boolean isCategoryDescriptionExist(CategoryDTO categoryDTO);
    CategoryDTO create(CategoryDTO categoryDTO) throws Exception;
    CategoryDTO update(Long categoryId, CategoryDTO categoryDTO);
}
