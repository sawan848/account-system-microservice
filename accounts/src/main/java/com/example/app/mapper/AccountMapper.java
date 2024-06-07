package com.example.app.mapper;

import com.example.app.dto.AccountsDto;
import com.example.app.entity.Accounts;

public class AccountMapper {

    public static Accounts mapToAccounts(AccountsDto accountsDto,Accounts accounts){
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());

        return accounts;
    }


      public static AccountsDto mapToAccountsDto(Accounts accounts,AccountsDto accountsDto){
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());

        return accountsDto;
    }



}
