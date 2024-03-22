package com.sbank.Account.repository;

import com.sbank.Account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account,Long> {

     Optional<Account> findByCustomerId(Long id);
}
