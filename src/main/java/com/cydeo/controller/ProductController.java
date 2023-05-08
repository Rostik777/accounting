package com.cydeo.controller;

import com.cydeo.dto.ProductDTO;
import com.cydeo.enums.ProductUnit;
import com.cydeo.service.CategoryService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String navigateToProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/product/product-list";
    }

    @GetMapping("/create")
    public String navigateToProductCreate(Model model) throws Exception {
        model.addAttribute("newProduct", new ProductDTO());
        return "/product/product-create";
    }

    @PostMapping("/create")
    public String createNewProduct(@Valid @ModelAttribute("newProduct") ProductDTO productDto, BindingResult bindingResult, Model model) throws Exception {

        if (productService.isProductNameExist(productDto)) {
            bindingResult.rejectValue("name", " ", "This Product Name already exists.");
        }

        if (bindingResult.hasErrors()) {
            return "/product/product-create";
        }
        productService.save(productDto);
        return "redirect:/products/list";
    }

    @ModelAttribute
    public void commonAttributes(Model model) throws Exception {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("productUnits", Arrays.asList(ProductUnit.values()));
    }
}
