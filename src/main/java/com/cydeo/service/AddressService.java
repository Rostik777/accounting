package com.cydeo.service;

import com.cydeo.dto.AddressDTO;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    AddressDTO findAddressById(Long id);
}
