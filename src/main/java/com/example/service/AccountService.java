package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
@Service
public class AccountService {

    public AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account account){
        Account existed = accountRepository.findAccountByUsername(account.getUsername());
        if(existed != null){
            return null;
        }else{
            accountRepository.save(account);
            return accountRepository.findAccountByUsername(account.getUsername());
        }
    }

    public Account login(Account account){
        Account searchedAccount = accountRepository.findAccountByUsername(account.getUsername());
        if (searchedAccount == null){
            return null;
        }else{
            if (searchedAccount.getPassword().equals(account.getPassword())){
                return searchedAccount;
            }else{
                return null;
            }
        }
    }
}
