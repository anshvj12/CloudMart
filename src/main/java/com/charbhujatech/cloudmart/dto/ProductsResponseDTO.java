package com.charbhujatech.cloudmart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ProductsResponseDTO {

    private int pageNumber;

    private int totalPages;

    private int pageSize;

    private long totalElements;

    private List<ProductResponseDTO> responseDTOList = new ArrayList<>();
}
