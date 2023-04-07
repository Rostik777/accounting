package com.cydeo.repository;

import com.cydeo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findAddressById(Long id);
}
