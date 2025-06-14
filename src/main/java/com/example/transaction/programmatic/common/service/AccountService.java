package com.example.transaction.programmatic.common.service;

import com.example.transaction.programmatic.common.model.Account;

public interface AccountService {
    Account createAccount(String name, double initialBalance); // For H2 where ID is set by code
    Account createAccountMySQL(String name, double initialBalance); // For MySQL where ID is auto-gen
    Account getAccount(int accountId);
    void transferFunds(int fromAccountId, int toAccountId, double amount) throws InsufficientFundsException;

    // Custom exception for business logic
    class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
}
