package com.sbank.Account.service;

import com.sbank.Account.dto.CustomerDto;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {
    public void createAccount(CustomerDto customerDTO);

    public CustomerDto fetchAccount(String mobileNumber);

    public Boolean updateAccount(CustomerDto customerDTO);

    public Boolean deleteAccount(String mobileNumber);
}
