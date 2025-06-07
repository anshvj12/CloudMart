package com.charbhujatech.cloudmart.dao;

import com.charbhujatech.cloudmart.Model.Card;
import com.charbhujatech.cloudmart.Model.CardProduct;
import com.charbhujatech.cloudmart.Model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardProductRepository extends JpaRepository<CardProduct,Long> {

    @Query(value = "SELECT cp FROM CardProduct cp WHERE cp.card = :cardId AND cp.product = :productId")
    Optional<CardProduct> findByCardAndUser(@Param(value="cardId") Card cardId, @Param(value="productId") Product productId);

    @Query(value = "DELETE FROM CardProduct cp WHERE cp.cardProductId =:cardProductId")
    @Modifying
    @Transactional
    void deleteByCardProductId(@Param(value = "cardProductId") Long cardProductId);
}
