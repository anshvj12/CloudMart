package com.charbhujatech.cloudmart.controller;

import com.charbhujatech.cloudmart.dto.CardResponseDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/cards/{cardId}/products/{productId}")
    public ResponseEntity<ResponseDTO> addCard(@PathVariable Long cardId, @PathVariable Long productId,
                                                   @RequestParam(defaultValue = "1") int quantity) {
        final ResponseDTO responseDTO = cardService.addProductToCard(cardId,productId,quantity);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/cards/{cardId}/products/{productId}")
    public ResponseEntity<ResponseDTO> removeCard(@PathVariable Long cardId, @PathVariable Long productId,
                                                      @RequestParam(defaultValue = "1") int quantity) {
        final ResponseDTO responseDTO = cardService.removeProductToCard(cardId,productId,quantity);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/cards/{cardId}")
    public ResponseEntity<CardResponseDTO> getCard (@PathVariable Long cardId)
    {
        final CardResponseDTO responseDTO = cardService.getCart(cardId);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}
