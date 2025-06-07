package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.dto.CardResponseDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;

public interface CardService {

    ResponseDTO addProductToCard(Long cardId, Long productId, int quantity);

    ResponseDTO removeProductToCard(Long cardId, Long productId, int quantity);

    CardResponseDTO getCart(Long cardId);
}
