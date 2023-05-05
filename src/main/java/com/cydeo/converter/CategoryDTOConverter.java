package com.cydeo.converter;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.service.CategoryService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
@ConfigurationPropertiesBinding
public class CategoryDTOConverter implements Converter<String, CategoryDTO> {
    private final CategoryService categoryService;

    public CategoryDTOConverter(@Lazy CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDTO convert(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        return categoryService.findCategoryById(Long.parseLong(id));
    }
}
