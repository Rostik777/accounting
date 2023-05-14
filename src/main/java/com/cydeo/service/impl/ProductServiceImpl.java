package com.cydeo.service.impl;

import com.cydeo.dto.ProductDTO;
import com.cydeo.entity.Company;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.Product;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.ProductService;
import com.cydeo.service.SecurityService;
import com.cydeo.utils.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final InvoiceProductService invoiceProductService;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil, SecurityService securityService, InvoiceProductService invoiceProductService) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.invoiceProductService = invoiceProductService;
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

    @Override
    public ProductDTO findProductById(Long productId) {
        Product product = productRepository.findById(productId).get();
        return mapperUtil.convert(product, new ProductDTO());
    }

    @Override
    public ProductDTO update(Long productId, ProductDTO productDTO) {
        productDTO.setId(productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new NoSuchElementException("Product " + productDTO.getName() + "not found"));
        final int quantityInStock = productDTO.getQuantityInStock() == null ? product.getQuantityInStock() : productDTO.getQuantityInStock();
        productDTO.setQuantityInStock(quantityInStock);
        product = productRepository.save(mapperUtil.convert(productDTO, new Product()));
        return mapperUtil.convert(product, productDTO);
    }

    @Override
    public void delete(Long productId) {
        Product product = productRepository.findById(productId).get();
        List<InvoiceProduct> invoiceProducts = invoiceProductService.findAllInvoiceProductsByProductId(product.getId());
        if (invoiceProducts.size() == 0 && product.getQuantityInStock() == 0){
            product.setIsDeleted(true);
        }else System.out.println("You cannot delete this product");
        productRepository.save(product);
    }
}
