package com.cydeo.service;

import com.cydeo.dto.AddressDTO;

public interface AddressService {
    AddressDTO findAddressById(Long id);
}
