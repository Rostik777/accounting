package com.cydeo.service;

import com.cydeo.dto.CompanyDTO;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {
    CompanyDTO findCompanyById(Long id);
}
