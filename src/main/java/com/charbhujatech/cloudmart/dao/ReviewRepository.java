package com.charbhujatech.cloudmart.dao;

import com.charbhujatech.cloudmart.Model.Product;
import com.charbhujatech.cloudmart.Model.Review;
import com.charbhujatech.cloudmart.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository  extends JpaRepository<Review, Long> {
    
    List<Review> findByUser(User user);

    List<Review> findByProduct(Product product);
}
