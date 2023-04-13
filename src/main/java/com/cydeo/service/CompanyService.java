package com.cydeo.service;

import com.cydeo.dto.CompanyDTO;

public interface CompanyService {
    CompanyDTO findCompanyById(Long id);
}
