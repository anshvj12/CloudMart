package com.charbhujatech.cloudmart.dao;

import com.charbhujatech.cloudmart.Model.Images;
import com.charbhujatech.cloudmart.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import com.online.shopping.Model.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {
    List<Images> findByProduct(Product product);

    @Modifying(flushAutomatically = true , clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM Images a WHERE a.imageId = :imageId")
    void deleteImageBYId(@Param(value = "imageId") Long imageId);
}
