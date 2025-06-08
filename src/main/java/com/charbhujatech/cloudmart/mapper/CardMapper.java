package com.charbhujatech.cloudmart.mapper;

import com.charbhujatech.cloudmart.Model.Card;
import com.charbhujatech.cloudmart.Model.CardProduct;
import com.charbhujatech.cloudmart.dto.CardResponseDTO;
import com.charbhujatech.cloudmart.dto.CardProductResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CardMapper {

    public static void mapToCardResponseDTO(CardResponseDTO cardResponseDTO, Card savedCard) {
        cardResponseDTO.setExpectedDeliveryDate(savedCard.getExpectedDeliveryDate());
        cardResponseDTO.setCardId(savedCard.getCardId());
        List<CardProductResponse> cardProductResponses = new ArrayList<>();
        final Set<CardProduct> products = savedCard.getCardProducts();
        if (products != null) {
            double totalPrice = 0;
            for( CardProduct cardProduct1 : products ) {
                CardProductResponse cardProductResponse = new CardProductResponse();
                if(cardProduct1.getProduct().getProductName() != null )
                {
                    cardProductResponse.setProductName(cardProduct1.getProduct().getProductName());
                }
                if(cardProduct1.getQuantity() > 0)
                {
                    cardProductResponse.setQuantity(cardProduct1.getQuantity());
                    totalPrice += (cardProduct1.getProduct().getPrice() * cardProduct1.getQuantity());

                }
                cardProductResponses.add(cardProductResponse);
            }
            cardResponseDTO.setTotalPrice(totalPrice);
        }
        cardResponseDTO.setProducts(cardProductResponses);
    }
}
