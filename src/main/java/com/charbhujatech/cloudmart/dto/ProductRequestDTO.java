package com.charbhujatech.cloudmart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductRequestDTO {

    private String productName;

    private Double price;

    private Integer availableQuantity;

    private Date productDate;

    private String productDescription;
}
