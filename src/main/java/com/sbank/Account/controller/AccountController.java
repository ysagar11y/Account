package com.sbank.Account.controller;

import com.sbank.Account.constant.AccountsConstant;
import com.sbank.Account.dto.CustomerDTO;
import com.sbank.Account.dto.ResponseDTO;
import com.sbank.Account.service.IAccountService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountController {

    @Autowired
    IAccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customer){
        accountService.createAccount(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountsConstant.STATUS_201,"User created"));

    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam
                                                           @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                           String mobileNumber){
        CustomerDTO customerDTO = accountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }
}
