package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.entity.InvoiceProduct;

import java.util.List;

public interface InvoiceProductService {
    InvoiceProductDTO findInvoiceProductById(long id);
    List<InvoiceProduct> findAllInvoiceProductsByProductId(Long id);
}
