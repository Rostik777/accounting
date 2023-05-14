package com.cydeo.service.impl;

import com.cydeo.entity.InvoiceProduct;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.InvoiceProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceProductImpl implements InvoiceProductService {
    private final InvoiceProductRepository invoiceProductRepository;

    public InvoiceProductImpl(InvoiceProductRepository invoiceProductRepository) {
        this.invoiceProductRepository = invoiceProductRepository;
    }

    @Override
    public List<InvoiceProduct> findAllInvoiceProductsByProductId(Long id) {
        return invoiceProductRepository.findAllInvoiceProductByProductId(id);
    }
}
