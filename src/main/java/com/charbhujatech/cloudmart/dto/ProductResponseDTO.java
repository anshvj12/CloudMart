package com.charbhujatech.cloudmart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductResponseDTO {

    private Long productId;

    private String productName;

    private Double price;

    private Integer availableQuantity;

    private Date productDate;

    private String productDescription;

    private List<ImageResponseDTO> imagesList = new ArrayList<>();

}
