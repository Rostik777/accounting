package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.enums.InvoiceType;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceService {
    InvoiceDTO findInvoiceById(long id);
    boolean checkIfInvoiceExist(Long clientVendorId);
    List<InvoiceDTO> getAllInvoicesOfCompany(InvoiceType invoiceType) throws Exception;
    BigDecimal getTotalPriceOfInvoice(Long id);
    BigDecimal getTotalTaxOfInvoice(Long id);
}
