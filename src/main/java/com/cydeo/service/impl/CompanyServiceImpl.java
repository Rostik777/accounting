package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.utils.MapperUtil;

public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public CompanyDTO findCompanyById(Long id) {
        return mapperUtil.convert(companyRepository.findCompanyById(id), new CompanyDTO());
    }
}
