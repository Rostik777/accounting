package com.cydeo.service;

import com.cydeo.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO findAddressById(Long id);
    List<String> getCountryList();
}
