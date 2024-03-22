package com.sbank.Account.service;

import com.sbank.Account.dto.CustomerDTO;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {
    public void createAccount(CustomerDTO customerDTO);

    public CustomerDTO fetchAccount(String mobileNumber);

    public Boolean updateAccount(CustomerDTO customerDTO);
}
