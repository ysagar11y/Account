package com.sbank.Account.mapper;

import com.sbank.Account.dto.CustomerDTO;
import com.sbank.Account.entity.Customer;

public class CustomerMapper {

    public static Customer mapToCustomer(CustomerDTO customerDTO, Customer customer){
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setMobileNumber(customerDTO.getMobileNumber());
        return customer;
    }

    public static CustomerDTO mapToCustomerDTO(Customer customer, CustomerDTO customerDTO){
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMobileNumber(customerDTO.getMobileNumber());
        return customerDTO;
    }
}
