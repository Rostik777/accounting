package com.cydeo.service;

import com.cydeo.dto.ClientVendorDTO;

import java.util.List;

public interface ClientVendorService {
    ClientVendorDTO findClientVendorById(Long id);
    List<ClientVendorDTO> getAllClientVendors();
    ClientVendorDTO create(ClientVendorDTO clientVendorDTO);
    boolean companyNameExists(ClientVendorDTO clientVendorDTO);
    ClientVendorDTO update(Long id, ClientVendorDTO clientVendorDTO) throws ClassNotFoundException, CloneNotSupportedException;
    void delete(Long id);
}
