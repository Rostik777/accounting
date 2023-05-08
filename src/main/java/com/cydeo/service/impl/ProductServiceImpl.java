package com.cydeo.service.impl;

import com.cydeo.dto.ProductDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.Product;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.ProductService;
import com.cydeo.service.SecurityService;
import com.cydeo.utils.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public List<ProductDTO> findAllProductsWithCategoryId(Long categoryId) {
        return productRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(product -> mapperUtil.convert(product, new ProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return productRepository.findAllByCategoryCompany(company)
                .stream()
                .sorted(Comparator.comparing((Product product) -> product.getCategory().getDescription())
                        .thenComparing(Product::getName))
                .map(each -> mapperUtil.convert(each, new ProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isProductNameExist(ProductDTO productDTO) {
        Company actualCompany = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        Product existingProduct = productRepository.findByNameAndCategoryCompany(productDTO.getName(), actualCompany);
        if (existingProduct == null) return false;
        return !existingProduct.getId().equals(productDTO.getId());
    }

    @Override
    public ProductDTO save(ProductDTO productDto) {
        Product product = mapperUtil.convert(productDto, new Product());
        product.setQuantityInStock(0);
        return mapperUtil.convert(productRepository.save(product), new ProductDTO());
    }
}
