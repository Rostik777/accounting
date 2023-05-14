package com.cydeo.service;

import com.cydeo.entity.InvoiceProduct;

import java.util.List;

public interface InvoiceProductService {
    List<InvoiceProduct> findAllInvoiceProductsByProductId(Long id);
}
