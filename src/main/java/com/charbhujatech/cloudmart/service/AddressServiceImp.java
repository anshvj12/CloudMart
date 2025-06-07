package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.Address;
import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.dao.AddressRepository;
import com.charbhujatech.cloudmart.dto.AddressRequestDTO;
import com.charbhujatech.cloudmart.dto.AddressResponseDTO;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.mapper.AddressMapper;
import com.charbhujatech.cloudmart.util.ConstantsString;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressServiceImp implements AddressService {

    private AddressRepository addressRepository;
    private UserServiceImp userServiceImp;

    @Override
    public Optional<AddressResponseDTO> getAddressById(Long address_id) {
        Optional<Address> byId = addressRepository.findById(address_id);
        if(byId.isPresent()){
            AddressResponseDTO responseDTO = new AddressResponseDTO();
            AddressMapper.mapToAddressResponseDTO(byId.get(), responseDTO);
            return Optional.of(responseDTO);
        }
        else
        {
            return Optional.empty();
        }
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public AddressResponseDTO saveAddress(Long userId, AddressRequestDTO addressRequestDTO) {

        Address saveAddress = new Address();
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

        if (addressRequestDTO !=null) {
            AddressMapper.mapToAddress(addressRequestDTO, saveAddress);

            if(userId > 0)
            {
                Optional<User> userById = userServiceImp.findUserById(userId);
                if(userById.isPresent()) {
                    saveAddress.setUser(userById.get());
                }
            }

            Address save = addressRepository.save(saveAddress);

            if (save != null) {
                AddressMapper.mapToAddressResponseDTO(save, addressResponseDTO);
            }
        }
        return addressResponseDTO;
    }




    @Override
    public List<AddressResponseDTO> getAddressByUserId(Long userId) {

        List<AddressResponseDTO> addressResponseDTOs = new ArrayList<>();

        if (userId > 0)
        {
            Optional<User> userById = userServiceImp.findUserById(userId);
            List<Address> addressesByUserId = addressRepository.findByUser(userById.get());

            if (!addressesByUserId.isEmpty())
            {

                for (Address address : addressesByUserId) {
                    AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
                    AddressMapper.mapToAddressResponseDTO(address,addressResponseDTO);
                    addressResponseDTOs.add(addressResponseDTO);
                }
            }
        }

        return addressResponseDTOs;
    }

    @Override
    public AddressResponseDTO updateAddress(Long userId, Long addressId, AddressRequestDTO addressRequestDTO) {
        Optional<Address> addressById = addressRepository.findById(addressId);
        if(addressById.isPresent())
        {
            Address address = addressById.get();
            AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
            AddressMapper.mapToAddress(addressRequestDTO,address);
            Address saveAddress = addressRepository.save(address);
            AddressMapper.mapToAddressResponseDTO(address,addressResponseDTO);
            return addressResponseDTO;
        }
        else
        {
            throw new ResourceNotFoundException(ConstantsString.ADDRESS_NOT_FOUND);
        }
    }

    @Override
    public Boolean deleteAddress(Long userId, Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.ADDRESS_NOT_FOUND));
        addressRepository.deleteByAddressId(addressId);
        return !addressRepository.existsById(addressId);
    }
}
