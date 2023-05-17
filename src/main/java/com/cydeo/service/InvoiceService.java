package com.cydeo.service;

import com.cydeo.dto.InvoiceDTO;

public interface InvoiceService {
    InvoiceDTO findInvoiceById(long id);
    boolean checkIfInvoiceExist(Long clientVendorId);
}
