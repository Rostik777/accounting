package com.cydeo.service.impl;

import com.cydeo.entity.Company;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.SecurityService;
import com.cydeo.utils.MapperUtil;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public boolean checkIfInvoiceExist(Long clientVendorId) {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return invoiceRepository.countAllByCompanyAndClientVendorId(company, clientVendorId) > 0;
    }
}
