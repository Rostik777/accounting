package com.cydeo.converter;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.service.ClientVendorService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ClientVendorDTOConverter implements Converter<String, ClientVendorDTO> {
    private final ClientVendorService clientVendorService;

    public ClientVendorDTOConverter(@Lazy ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @Override
    public ClientVendorDTO convert(String id) {
        if (id == null || id.isBlank())
            return null;
        return clientVendorService.findClientVendorById(Long.parseLong(id));
    }
}
