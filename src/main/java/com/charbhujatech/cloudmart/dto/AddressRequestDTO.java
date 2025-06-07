package com.charbhujatech.cloudmart.dto;

import com.charbhujatech.cloudmart.enums.AddressType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDTO {

    protected String firstLine;

    protected String secondLine;

    protected String landmarks;

    protected String postalCode;

    protected String city;

    protected String state;

    protected String country;

    protected AddressType addressType;
}
