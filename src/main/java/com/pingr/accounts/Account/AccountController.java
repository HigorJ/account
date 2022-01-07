package com.pingr.accounts.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{userId}")
    public Account getAccount(@PathVariable("userId") Long userId) {
        return this.accountService.getAccount(userId);
    }

    @GetMapping
    public List<AccountIdAndUsername> searchByUsernameAlike(@RequestParam("usernameAlike") String usernameAlike) {
        return this.accountService.searchByUsernameAlike(usernameAlike);
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return this.accountService.createAccount(account);
    }

    @PutMapping("/{userId}")
    public Account updateAccount(@PathVariable("userId") Long userId, @RequestBody Account newData) {
        return this.accountService.updateAccount(userId, newData);
    }

    @DeleteMapping("/{userId}")
    public String deleteAccount(@PathVariable("userId") Long userId) {
        this.accountService.deleteAccount(userId);

        return "Conta deletada com sucesso!";
    }
}
