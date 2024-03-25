package com.sbank.Account.mapper;



import com.sbank.Account.dto.AccountDTO;
import com.sbank.Account.entity.Account;


public class AccountMapper {

    public static Account mapToAccount(Account account,AccountDTO accountDTO) {
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType());
        account.setBranchAddress(accountDTO.getBranchAddress());
        return account;
    }

    public static AccountDTO mapToAccountDTO(Account account, AccountDTO accountDTO){
        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setBranchAddress(account.getBranchAddress());
        return accountDTO;
    }

}
