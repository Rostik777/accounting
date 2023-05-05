package com.cydeo.service;

import com.cydeo.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductsWithCategoryId(Long categoryId);
}
