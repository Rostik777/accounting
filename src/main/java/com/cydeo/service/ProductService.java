package com.cydeo.service;

import com.cydeo.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductsWithCategoryId(Long categoryId);
    List<ProductDTO> getAllProducts();
    boolean isProductNameExist(ProductDTO productDto);
    ProductDTO save(ProductDTO productDto);
    ProductDTO findProductById(Long productId);
    ProductDTO update(Long productId, ProductDTO productDTO);
    void delete(Long productId);
}
