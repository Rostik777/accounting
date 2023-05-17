package com.cydeo.converter;

import com.cydeo.dto.InvoiceDTO;
import com.cydeo.service.InvoiceService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class InvoiceDTOConverter implements Converter<String, InvoiceDTO> {
    private final InvoiceService invoiceService;

    public InvoiceDTOConverter(@Lazy InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public InvoiceDTO convert(String id) {
        return invoiceService.findInvoiceById(Long.parseLong(id));
    }
}
