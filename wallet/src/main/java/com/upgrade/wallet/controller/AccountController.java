package com.upgrade.wallet.controller;

import com.upgrade.wallet.entities.Account;
import com.upgrade.wallet.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * API endpoint to create an account for a user
 */
@RestController
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/api/account")
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

}
