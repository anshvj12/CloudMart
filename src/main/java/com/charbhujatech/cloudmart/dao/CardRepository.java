package com.charbhujatech.cloudmart.dao;

import com.charbhujatech.cloudmart.Model.Card;
import com.charbhujatech.cloudmart.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByUser(User userId);

}
