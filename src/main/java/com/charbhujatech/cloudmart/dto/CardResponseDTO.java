package com.charbhujatech.cloudmart.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CardResponseDTO {

    private Long cardId;

    protected double totalPrice;

    protected Date expectedDeliveryDate;

    protected List<CardProductResponse> products;
}
