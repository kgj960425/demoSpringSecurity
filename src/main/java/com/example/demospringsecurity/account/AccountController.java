package com.example.demospringsecurity.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AccountController {
    @Autowired
    AccountService accountS;

    @GetMapping("/create")
    public Account create(){
        Account account = new Account();
        account.setEmail("kgj@email.com");
        account.setPassword("password");
        System.out.print("account : "+account);

        return accountS.save(account);
    }
}
