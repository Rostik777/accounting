package com.cydeo.service;

import com.cydeo.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {
    CompanyDTO findCompanyById(Long id);
    List<CompanyDTO> getAllCompanies();
}
