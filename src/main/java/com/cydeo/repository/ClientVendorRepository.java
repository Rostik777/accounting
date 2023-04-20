package com.cydeo.repository;

import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {
    ClientVendor findClientVendorById(Long id);
    List<ClientVendor> findAllByCompany(Company company);
    ClientVendor findByClientVendorNameAndCompany(String companyName, Company actualCompany);
}
