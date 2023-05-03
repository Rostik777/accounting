package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDTO;

import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.SecurityService;
import com.cydeo.utils.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {
    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public ClientVendorDTO findClientVendorById(Long id) {
        ClientVendor clientVendor = clientVendorRepository.findClientVendorById(id);
        return mapperUtil.convert(clientVendor, new ClientVendorDTO());
    }

    @Override
    public List<ClientVendorDTO> getAllClientVendors() {
        Company company = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        return clientVendorRepository
                .findAllByCompany(company)
                .stream()
                .sorted(Comparator.comparing(ClientVendor::getClientVendorType).reversed()
                        .thenComparing(ClientVendor::getClientVendorName))
                .map(each -> mapperUtil.convert(each, new ClientVendorDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public ClientVendorDTO create(ClientVendorDTO clientVendorDTO) {
        clientVendorDTO.setCompany(securityService.getLoggedInUser().getCompany());
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());
        return mapperUtil.convert(clientVendorRepository.save(clientVendor), new ClientVendorDTO());
    }

    @Override
    public boolean companyNameExists(ClientVendorDTO clientVendorDTO) {
        Company actualCompany = mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company());
        ClientVendor existingClientVendor = clientVendorRepository.findByClientVendorNameAndCompany(clientVendorDTO.getClientVendorName(), actualCompany);
        if (existingClientVendor == null) return false;
        return !existingClientVendor.getId().equals(clientVendorDTO.getId());
    }

    @Override
    public ClientVendorDTO update(Long clientVendorId, ClientVendorDTO clientVendorDTO) throws ClassNotFoundException, CloneNotSupportedException {
        ClientVendor savedClientVendor = clientVendorRepository.findClientVendorById(clientVendorId);
        clientVendorDTO.getAddress().setId(savedClientVendor.getAddress().getId());
        clientVendorDTO.setCompany(securityService.getLoggedInUser().getCompany());
        ClientVendor updatedClientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());
        return mapperUtil.convert(clientVendorRepository.save(updatedClientVendor), clientVendorDTO);
    }
}
