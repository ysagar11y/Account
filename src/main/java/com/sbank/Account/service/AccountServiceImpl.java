package com.sbank.Account.service;

import com.sbank.Account.constant.AccountsConstant;
import com.sbank.Account.dto.AccountsDto;
import com.sbank.Account.dto.CustomerDto;
import com.sbank.Account.entity.Accounts;
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
    public void createAccount(CustomerDto customerDTO) {

        Customer customer = CustomerMapper.mapToCustomer(customerDTO,new Customer());
        Optional<Customer> OptionalCustomer = customerRepo.findByMobileNumber(customer.getMobileNumber());
        if(OptionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("The customer already exist");
        }

        Customer savedCustomer = customerRepo.save(customer);
        Accounts accounts = accountRepo.save(createNewAccount(savedCustomer));
    }


    public static Accounts createNewAccount(Customer customer){
        Accounts accounts = new Accounts();
        accounts.setAccountType(AccountsConstant.SAVINGS);
        accounts.setBranchAddress(AccountsConstant.ADDRESS);
        accounts.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        accounts.setAccountNumber(randomAccNumber);
        return accounts;
    }



    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public Boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountRepo.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountRepo.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepo.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepo.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    public Boolean deleteAccount(String mobileNumber){
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepo.deleteByCustomerId(customer.getCustomerId());
        customerRepo.deleteById(customer.getCustomerId());
        return true;
    }

}
