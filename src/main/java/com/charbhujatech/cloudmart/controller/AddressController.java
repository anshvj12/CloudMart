package com.charbhujatech.cloudmart.controller;

import com.charbhujatech.cloudmart.dao.UserRepository;
import com.charbhujatech.cloudmart.dto.AddressRequestDTO;
import com.charbhujatech.cloudmart.dto.AddressResponseDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.service.AddressService;
import com.charbhujatech.cloudmart.util.ConstantsString;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserRepository userRepository;

    @PostMapping("users/{userId}/addresses")
    public ResponseEntity<AddressResponseDTO> saveAddress(@PathVariable Long userId,
                                                          @RequestBody AddressRequestDTO addressRequestDTO) {
        AddressResponseDTO addressResponseDTO = addressService.saveAddress(userId,addressRequestDTO);
        if ( addressResponseDTO != null ) {
            return new ResponseEntity<>(addressResponseDTO,HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(addressResponseDTO,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("users/{userId}/addresses/{addressId}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long userId,
                                                             @PathVariable Long addressId) {
        Optional<AddressResponseDTO> address = addressService.getAddressById(addressId);
        if(address.isPresent())
            return new ResponseEntity<>(address.get(),HttpStatus.OK);
        else
            throw new ResourceNotFoundException(ConstantsString.ADDRESS_NOT_FOUND);
    }

    @GetMapping("users/{userId}/addresses")
    public ResponseEntity<List<AddressResponseDTO>> getAddressByUserId(@PathVariable Long userId) {
        List<AddressResponseDTO> addressList = addressService.getAddressByUserId(userId);
        if (addressList.isEmpty())
        {
            throw new ResourceNotFoundException(ConstantsString.ADDRESS_NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(addressList,HttpStatus.OK);
        }
    }

    @PutMapping("users/{userId}/addresses/{addressId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long userId,
                                                            @PathVariable Long addressId
            , @RequestBody AddressRequestDTO addressRequestDTO){
        AddressResponseDTO responseDTO = addressService.updateAddress(userId,addressId,addressRequestDTO);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @DeleteMapping("users/{userId}/addresses/{addressId}")
    public ResponseEntity<ResponseDTO> deleteAddress(@PathVariable Long userId,
                                                     @PathVariable Long addressId){
        return addressService.deleteAddress(userId,addressId)
                ? new ResponseEntity<>(new ResponseDTO(HttpStatus.OK.toString(), ConstantsString.ADDRESS_DELETED),HttpStatus.OK)
                : new ResponseEntity<>(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ConstantsString.ADDRESS_DELETED_FAILED),HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
