package com.sbank.Account.service;

import com.sbank.Account.constant.AccountsConstant;
import com.sbank.Account.dto.AccountDTO;
import com.sbank.Account.dto.CustomerDTO;
import com.sbank.Account.entity.Account;
import com.sbank.Account.entity.Customer;
import com.sbank.Account.exception.CustomerAlreadyExistsException;
import com.sbank.Account.exception.ResourceNotFoundException;
import com.sbank.Account.mapper.AccountMapper;
import com.sbank.Account.mapper.CustomerMapper;
import com.sbank.Account.repository.AccountRepo;
import com.sbank.Account.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    AccountRepo accountRepo;


    @Override
    public void createAccount(CustomerDTO customerDTO) {

        Customer customer = CustomerMapper.mapToCustomer(customerDTO,new Customer());
        Optional<Customer> OptionalCustomer = customerRepo.findByMobileNumber(customer.getMobileNumber());
        if(OptionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("The customer already exist");
        }

        Customer savedCustomer = customerRepo.save(customer);
        Account account = accountRepo.save(createNewAccount(savedCustomer));
    }


    public static Account createNewAccount(Customer customer){
        Account account = new Account();
        account.setAccountType(AccountsConstant.SAVINGS);
        account.setBranchAddress(AccountsConstant.ADDRESS);
        account.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        account.setAccountNumber(randomAccNumber);
        return account;
    }



    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Account account = accountRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","Customer ID", customer.getCustomerId().toString()));
        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer,new CustomerDTO());
        customerDTO.setAccountDTO(AccountMapper.mapToAccountDTO(account,new AccountDTO()));
        return customerDTO;
    }

    @Override
    public Boolean updateAccount(CustomerDTO customerDTO) {
        Boolean isUpdate = false;
        AccountDTO accountDTO = customerDTO.getAccountDTO();
        Account account = accountRepo.findById(accountDTO.getAccountNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "Account", accountDTO.getAccountNumber().toString())
        );

        AccountMapper.mapToAccount(account,accountDTO);
        account = accountRepo.save(account);

        Long customerId= account.getCustomerId();
        Customer customer = customerRepo.findById(account.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Customer", customerId.toString())
        );

        CustomerMapper.mapToCustomer(customerDTO,customer);
        customerRepo.save(customer);

        isUpdate = true;

        return isUpdate;
    }
}
