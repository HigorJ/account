package com.pingr.accounts.Account.exceptions;

public class InvalidAccountGetException extends RuntimeException {
    public InvalidAccountGetException(String details) {
        super("Falha ao buscar Conta: " + details);
    }
}
