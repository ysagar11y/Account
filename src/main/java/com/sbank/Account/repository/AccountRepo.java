package com.sbank.Account.repository;

import com.sbank.Account.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Accounts,Long> {

     Optional<Accounts> findByCustomerId(Long customerId);

     @Transactional
     @Modifying
     void deleteByCustomerId(Long customerId);
}
