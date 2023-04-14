package com.cydeo.service.impl;

import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.Company;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.utils.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
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

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository
                .findAll()
                .stream()
                .filter(company -> company.getId() != 1)
                .sorted(Comparator.comparing(Company::getCompanyStatus).thenComparing(Company::getTitle))
                .map(each -> mapperUtil.convert(each, new CompanyDTO()))
                .collect(Collectors.toList());
    }
}
