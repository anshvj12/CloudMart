package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.Card;
import com.charbhujatech.cloudmart.Model.CardProduct;
import com.charbhujatech.cloudmart.Model.Product;
import com.charbhujatech.cloudmart.dao.CardProductRepository;
import com.charbhujatech.cloudmart.dao.CardRepository;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.exception.BadRequestException;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.mapper.CardMapper;
import com.charbhujatech.cloudmart.dto.CardResponseDTO;
import com.charbhujatech.cloudmart.util.ConstantsString;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CardServiceImp implements CardService {

    private final ProductServiceImp productService;
    private final CardRepository cardRepository;
    private final UserServiceImp userServiceImp;
    private final CardProductRepository cardProductRepository;


    @Override
    @Transactional
    public ResponseDTO addProductToCard(Long cardId, Long productId, int quantity) {

        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.CARD_NOT_FOUND)
        );
        Optional<Product> productById = productService.getProductById(productId);
        if (productById.isPresent()) {
            Product product = productById.get();
            if ( product.getAvailableQuantity() >= quantity ) {

                if( quantity < 1 )
                    throw new BadRequestException(ConstantsString.PRODUCT_QUANTITY_ZERO);

                Optional<CardProduct>  cardAndUser = cardProductRepository.findByCardAndUser(card, product);

                CardProduct save = null;

                if (cardAndUser.isPresent())
                {
                    CardProduct cardProduct1 = cardAndUser.get();
                    cardProduct1.setQuantity(cardProduct1.getQuantity()+quantity);
                    save = cardProductRepository.saveAndFlush(cardProduct1);
                }
                else
                {
                    CardProduct cardProduct = new CardProduct();
                    cardProduct.setCard(card);
                    cardProduct.setProduct(product);
                    cardProduct.setQuantity(quantity);
                    save = cardProductRepository.saveAndFlush(cardProduct);
                }

                ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.toString(), ConstantsString.PRODUCT_ADDED_CARD);

                return responseDTO;
            }
            else
            {
                throw new BadRequestException(ConstantsString.PRODUCT_QUANTITY_NOT_AVAILABLE);
            }
        }
        else
        {
            throw new ResourceNotFoundException(ConstantsString.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public ResponseDTO removeProductToCard(Long cardId, Long productId, int quantity) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.CARD_NOT_FOUND)
        );

        Optional<Product> productById = productService.getProductById(productId);
        if (productById.isPresent()) {
            Product product = productById.get();

            Optional<CardProduct>  cardAndUser = cardProductRepository.findByCardAndUser(card, product);
            if (cardAndUser.isPresent())
            {
                final CardProduct cardProduct1 = cardAndUser.get();

                if( quantity < 1 )
                    throw new BadRequestException(ConstantsString.PRODUCT_QUANTITY_ZERO);

                if(cardProduct1.getQuantity() > quantity )
                {
                    cardProduct1.setQuantity(cardProduct1.getQuantity()-quantity);
                    CardProduct save = cardProductRepository.save(cardProduct1);
                    cardProductRepository.flush();
                }
                else if (cardProduct1.getQuantity() == quantity )
                {
                    cardProductRepository.deleteByCardProductId(cardProduct1.getCardProductId());
                    cardProductRepository.flush();
                }
                else
                {
                    throw new BadRequestException(ConstantsString.PRODUCT_QUANTITY_NOT_AVAILABLE);
                }
            }
            else
            {
                throw new BadRequestException(ConstantsString.PRODUCT_IS_NOT_IN_CARD);
            }

            ResponseDTO responseDTO = new ResponseDTO(HttpStatus.OK.toString(), ConstantsString.PRODUCT_REMOVED_CARD);
            return responseDTO;
        }else{
            throw new ResourceNotFoundException(ConstantsString.PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public CardResponseDTO getCart(Long cardId) {
        final Card savedCard = cardRepository.findById(cardId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.CARD_NOT_FOUND)
        );
        CardResponseDTO cardResponseDTO = new CardResponseDTO();
        if (savedCard != null) {
            CardMapper.mapToCardResponseDTO(cardResponseDTO, savedCard);
        }
        return cardResponseDTO;
    }

}
