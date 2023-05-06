package com.cydeo.controller;

import com.cydeo.dto.CategoryDTO;
import com.cydeo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String navigateToCategoryList(Model model) throws Exception {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/category/category-list";
    }

    @GetMapping("/create")
    public String navigateToCategoryCreate(Model model) {
        model.addAttribute("newCategory", new CategoryDTO());
        return "/category/category-create";
    }

    @PostMapping("/create")
    public String createNewCategory(@Valid @ModelAttribute("newCategory") CategoryDTO categoryDto, BindingResult bindingResult) throws Exception {

        boolean categoryDescriptionExist = categoryService.isCategoryDescriptionExist(categoryDto);

        if (categoryDescriptionExist) {
            bindingResult.rejectValue("description", " ", "This category description already exists");
        }

        if (bindingResult.hasErrors()) {
            return "/category/category-create";
        }

        categoryService.create(categoryDto);
        return "redirect:/categories/list";
    }
}
