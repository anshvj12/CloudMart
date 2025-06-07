package com.charbhujatech.cloudmart.mapper;

import com.charbhujatech.cloudmart.Model.Address;
import com.charbhujatech.cloudmart.dto.AddressRequestDTO;
import com.charbhujatech.cloudmart.dto.AddressResponseDTO;

public class AddressMapper {

    public static void mapToAddress(AddressRequestDTO addressRequestDTO, Address saveAddress) {

        if(addressRequestDTO.getFirstLine() != null && !addressRequestDTO.getFirstLine().equals(""))
            saveAddress.setFirstLine(addressRequestDTO.getFirstLine());
        if(addressRequestDTO.getSecondLine() != null && !addressRequestDTO.getSecondLine().trim().equals(""))
            saveAddress.setSecondLine(addressRequestDTO.getSecondLine());
        if(addressRequestDTO.getLandmarks() != null && !addressRequestDTO.getLandmarks().isEmpty())
            saveAddress.setLandmarks(addressRequestDTO.getLandmarks());
        if(addressRequestDTO.getPostalCode() != null && !addressRequestDTO.getPostalCode().equals(""))
            saveAddress.setPostalCode(addressRequestDTO.getPostalCode());
        if(addressRequestDTO.getCity() != null)
            saveAddress.setCity(addressRequestDTO.getCity());
        if(addressRequestDTO.getState() != null && !addressRequestDTO.getState().isEmpty())
            saveAddress.setState(addressRequestDTO.getState());
        if(addressRequestDTO.getCountry() != null && !addressRequestDTO.getCountry().isEmpty())
            saveAddress.setCountry(addressRequestDTO.getCountry());
        if(addressRequestDTO.getAddressType() != null)
            saveAddress.setAddressType(addressRequestDTO.getAddressType());

    }

    public static void mapToAddressResponseDTO(Address save, AddressResponseDTO addressResponseDTO) {
        if( save.getAddressId()>0)
        {
            addressResponseDTO.setAddressId(save.getAddressId());
        }
        if( save.getFirstLine() != null && !save.getFirstLine().equals(""))
            addressResponseDTO.setFirstLine(save.getFirstLine());
        if(save.getSecondLine() != null && !save.getSecondLine().trim().equals(""))
            addressResponseDTO.setSecondLine(save.getSecondLine());
        if(save.getLandmarks() != null && !save.getLandmarks().isEmpty())
            addressResponseDTO.setLandmarks(save.getLandmarks());
        if(save.getPostalCode() != null && !save.getPostalCode().equals(""))
            addressResponseDTO.setPostalCode(save.getPostalCode());
        if(save.getCity() != null && !save.getCity().equals(""))
            addressResponseDTO.setCity(save.getCity());
        if(save.getState() != null && !save.getState().isEmpty())
            addressResponseDTO.setState(save.getState());
        if(save.getCountry() != null && !save.getCountry().equals(""))
            addressResponseDTO.setCountry(save.getCountry());
        if(save.getAddressType() != null)
            addressResponseDTO.setAddressType(save.getAddressType());
    }

}
