package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.SecurityService;
import com.cydeo.utils.MapperUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final InvoiceProductService invoiceProductService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, SecurityService securityService, InvoiceProductService invoiceProductService) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public InvoiceDTO findInvoiceById(long id) {
        return mapperUtil.convert(invoiceRepository.findInvoiceById(id), new InvoiceDTO());
    }

    @Override
    public boolean checkIfInvoiceExist(Long clientVendorId) {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return invoiceRepository.countAllByCompanyAndClientVendorId(company, clientVendorId) > 0;
    }

    @Override
    public List<InvoiceDTO> getAllInvoicesOfCompany(InvoiceType invoiceType) {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return invoiceRepository.findInvoicesByCompanyAndInvoiceType(company, invoiceType)
                .stream()
                .sorted(Comparator.comparing(Invoice::getInvoiceNo))
                .map(each -> mapperUtil.convert(each, new InvoiceDTO()))
                .peek(this::calculateInvoiceDetails)
                .collect(Collectors.toList());
    }

    private void calculateInvoiceDetails(InvoiceDTO invoiceDTO) {
        invoiceDTO.setPrice(getTotalPriceOfInvoice(invoiceDTO.getId()));
        invoiceDTO.setTax(getTotalTaxOfInvoice(invoiceDTO.getId()));
        invoiceDTO.setTotal(getTotalPriceOfInvoice(invoiceDTO.getId()).add(getTotalTaxOfInvoice(invoiceDTO.getId())));
    }

    @Override
    public BigDecimal getTotalPriceOfInvoice(Long id){
        Invoice invoice = invoiceRepository.findInvoiceById(id);
        List<InvoiceProductDTO> invoiceProductsOfInvoice = invoiceProductService.getInvoiceProductsOfInvoice(invoice.getId());
        return invoiceProductsOfInvoice.stream()
                .map(p -> p.getPrice()
                        .multiply(BigDecimal.valueOf((long) p.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getTotalTaxOfInvoice(Long id){
        Invoice invoice = invoiceRepository.findInvoiceById(id);
        List<InvoiceProductDTO> invoiceProductsOfInvoice = invoiceProductService.getInvoiceProductsOfInvoice(invoice.getId());
        return invoiceProductsOfInvoice.stream()
                .map(p -> p.getPrice()
                        .multiply(BigDecimal.valueOf(p.getQuantity() * p.getTax() /100d))
                        .setScale(2, RoundingMode.HALF_UP))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public InvoiceDTO getNewInvoice(InvoiceType invoiceType) throws Exception {
        InvoiceDTO invoiceDto = new InvoiceDTO();
        invoiceDto.setInvoiceNo(generateInvoiceNo(invoiceType));
        invoiceDto.setDate(LocalDate.now());
        return invoiceDto;
    }

    @Override
    public InvoiceDTO save(InvoiceDTO invoiceDTO, InvoiceType invoiceType) {
        invoiceDTO.setCompany(securityService.getLoggedInUser().getCompany());
        invoiceDTO.setInvoiceType(invoiceType);
        invoiceDTO.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        Invoice invoice = mapperUtil.convert(invoiceDTO, new Invoice());
        return mapperUtil.convert(invoiceRepository.save(invoice), new InvoiceDTO());
    }

    @Override
    public InvoiceDTO update(Long invoiceId, InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceRepository.findInvoiceById(invoiceId);
        invoice.setClientVendor(mapperUtil.convert(invoiceDTO.getClientVendor(), new ClientVendor()));
        invoiceRepository.save(invoice);
        return mapperUtil.convert(invoice, invoiceDTO);
    }

    private String generateInvoiceNo(InvoiceType invoiceType){
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        List<Invoice> invoices = invoiceRepository.findInvoicesByCompanyAndInvoiceType(company, invoiceType);
        if (invoices.size() == 0) {
            return invoiceType.name().charAt(0) + "-001";
        }
        Invoice lastCreatedInvoiceOfTheCompany = invoices.stream()
                .max(Comparator.comparing(Invoice::getInsertDateTime)).get();
        int newOrder = Integer.parseInt(lastCreatedInvoiceOfTheCompany.getInvoiceNo().substring(2)) + 1;
        return invoiceType.name().charAt(0) + "-" + String.format("%03d", newOrder);
    }
}
