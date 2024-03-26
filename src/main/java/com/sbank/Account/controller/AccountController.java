package com.sbank.Account.controller;

import com.sbank.Account.constant.AccountsConstant;
import com.sbank.Account.dto.CustomerDto;
import com.sbank.Account.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customer){
        accountService.createAccount(customer);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstant.STATUS_201,"User created"));

    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                           @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                           String mobileNumber){
        CustomerDto customerDTO = accountService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody CustomerDto customerDTO){
        if(accountService.updateAccount(customerDTO)){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstant.STATUS_200,AccountsConstant.MESSAGE_200));
        }
        else
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstant.STATUS_417,AccountsConstant.MESSAGE_417_UPDATE));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                            String mobileNumber){
        if(accountService.deleteAccount(mobileNumber)){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body( new ResponseDto(AccountsConstant.STATUS_200,AccountsConstant.MESSAGE_200));
        }

        else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstant.STATUS_417,AccountsConstant.MESSAGE_417_DELETE));
        }
    }


}
