package com.pingr.accounts.Account;

import com.pingr.accounts.Account.exceptions.InvalidAccountCreationException;
import com.pingr.accounts.Account.exceptions.InvalidAccountGetException;
import com.pingr.accounts.Account.exceptions.InvalidAccountUpdateException;
import com.pingr.accounts.Account.exceptions.InvalidArgumentsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final ProducerService producerService;

    @Autowired
    public AccountService(AccountRepository accountRepository, ProducerService producerService) {
        this.accountRepository = accountRepository;
        this.producerService = producerService;
    }

    public Account getAccount(Long userId) {
        if(userId == null) throw new InvalidArgumentsException("ID de usuário é necessário!");

        Optional<Account> optionalAccount = this.accountRepository.findById(userId);
        if(!optionalAccount.isPresent()) throw new InvalidAccountGetException("Usuário não existe!");

        Account account = optionalAccount.get();

        return account;
    }

    public List<AccountIdAndUsername> searchByUsernameAlike(String usernameAlike) {
        if (usernameAlike.length() == 0) throw new InvalidArgumentsException("termo de busca vazio");

        return this.accountRepository.searchByUsernameAlike(usernameAlike);
    }

    public Account createAccount(Account account) {
        if (account == null) throw new InvalidAccountCreationException("conta não pode ser nula");

        try {
            Account savedAccount = this.accountRepository.save(account);
            this.producerService.accountCreated(savedAccount);

            return savedAccount;
        } catch (Exception e) {
            throw new InvalidAccountCreationException("conta inválida para criação");
        }
    }

    public Account updateAccount(Long userId, Account newData) {
        if(userId == null || ObjectUtils.isEmpty(newData)) throw new InvalidArgumentsException("ID de usuário e dados para atualizar são necessários!");

        Optional<Account> verifyAccount = this.accountRepository.findById(userId);
        if(!verifyAccount.isPresent()) throw new InvalidAccountGetException("Usuário não existe!");

        try {
            Account accountToUpdate = verifyAccount.get();
            newData.setId(accountToUpdate.getId());
            this.accountRepository.save(newData);

            this.producerService.accountUpdated(newData);

            return newData;
        } catch (Exception e) {
            throw new InvalidAccountUpdateException("Erro ao atualizar Usuário!");
        }
    }

    public void deleteAccount(Long userId) {
        if(userId == null) throw new InvalidArgumentsException("ID de usuário é necessário!");

        try {
            Optional<Account> optionalAccount = this.accountRepository.findById(userId);

            if(!optionalAccount.isPresent()) throw new InvalidAccountGetException("Usuário não existe!");

            Account account = optionalAccount.get();

            this.producerService.accountDeleted(account);
            this.accountRepository.deleteById(userId);
        } catch (Exception e) {
            throw new Error("Erro ao deletar a conta!");
        }
    }
}
