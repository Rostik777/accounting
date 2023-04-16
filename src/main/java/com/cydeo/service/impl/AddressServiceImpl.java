package com.cydeo.service.impl;

import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.addressApi.Country;
import com.cydeo.dto.addressApi.TokenDTO;
import com.cydeo.repository.AddressRepository;
import com.cydeo.service.AddressService;
import com.cydeo.service.feignClients.AddressFeignClient;
import com.cydeo.utils.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;
    private final AddressFeignClient addressFeignClient;

    public AddressServiceImpl(AddressRepository addressRepository, MapperUtil mapperUtil, AddressFeignClient addressFeignClient) {
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
        this.addressFeignClient = addressFeignClient;
    }

    @Value("${address.api.user-email}")
    private String email;

    @Value("${address.api.api-token}")
    private String userToken;

    @Override
    public AddressDTO findAddressById(Long id) {
        return mapperUtil.convert(addressRepository.findAddressById(id), new AddressDTO());
    }

    private String getBearerToken() {
        TokenDTO bearerToken = addressFeignClient.auth(this.email, this.userToken);
        log.info("Retrieved token for address api: " + bearerToken.getAuthToken());
        return "Bearer " + bearerToken.getAuthToken();
    }

    @Override
    public List<String> getCountryList() {
        List<Country> countries = addressFeignClient.getCountryList(getBearerToken());

        log.info("Total Country size is :" + countries.size());
        return countries.stream()
                .map(Country::getCountryName)
                .collect(Collectors.toList());
    }
}
