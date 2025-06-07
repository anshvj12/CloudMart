package com.charbhujatech.cloudmart.dao;

import com.charbhujatech.cloudmart.Model.Address;
import com.charbhujatech.cloudmart.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("DELETE FROM Address a WHERE a.addressId = :addressId")
    void deleteByAddressId(@Param("addressId") Long addressId);
}
