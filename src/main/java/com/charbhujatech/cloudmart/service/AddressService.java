package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.Address;
import com.charbhujatech.cloudmart.dto.AddressRequestDTO;
import com.charbhujatech.cloudmart.dto.AddressResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AddressService {

    Optional<AddressResponseDTO> getAddressById(Long address_id);

    Address saveAddress(Address address);

    AddressResponseDTO saveAddress(Long userId, AddressRequestDTO addressRequestDTO);

    List<AddressResponseDTO> getAddressByUserId(Long userId);

    AddressResponseDTO updateAddress(Long userId, Long addressId, AddressRequestDTO addressRequestDTO);

    Boolean deleteAddress(Long userId, Long addressId);
}
