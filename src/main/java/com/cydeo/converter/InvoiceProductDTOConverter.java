package com.cydeo.converter;

import com.cydeo.dto.InvoiceProductDTO;
import com.cydeo.service.InvoiceProductService;

import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class InvoiceProductDTOConverter implements Converter<String, InvoiceProductDTO> {
    private final InvoiceProductService invoiceProductService;

    public InvoiceProductDTOConverter(@Lazy InvoiceProductService invoiceProductService) {
        this.invoiceProductService = invoiceProductService;
    }

    @SneakyThrows
    @Override
    public InvoiceProductDTO convert(String id) {
        return invoiceProductService.findInvoiceProductById(Long.parseLong(id));
    }
}
