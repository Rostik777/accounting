package com.cydeo.service.impl;

import com.cydeo.dto.AddressDTO;
import com.cydeo.repository.AddressRepository;
import com.cydeo.service.AddressService;
import com.cydeo.utils.MapperUtil;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;

    public AddressServiceImpl(AddressRepository addressRepository, MapperUtil mapperUtil) {
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public AddressDTO findAddressById(Long id) {
        return mapperUtil.convert(addressRepository.findAddressById(id), new AddressDTO());
    }
}
