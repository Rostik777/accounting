package com.cydeo.service;

import com.cydeo.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {
    CompanyDTO findCompanyById(Long id);
    List<CompanyDTO> getAllCompanies();
    CompanyDTO create(CompanyDTO companyDto);
    boolean isTitleExist(String title);
    CompanyDTO update(Long companyId, CompanyDTO companyDTO);
    void activate(Long companyId);
    void deactivate(Long companyId);
    List<CompanyDTO> getFilteredCompaniesForCurrentUser();
    CompanyDTO getCompanyByLoggedInUser();
}
