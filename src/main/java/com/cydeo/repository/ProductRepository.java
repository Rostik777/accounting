package com.cydeo.repository;

import com.cydeo.entity.Company;
import com.cydeo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findAllByCategoryCompany(Company company);
    Product findByNameAndCategoryCompany(String name, Company actualCompany);
}
