package com.example.transaction.common.dao;

import com.example.transaction.common.model.Account;

public interface AccountDao {
    void createAccount(Account account);
    Account findAccountById(int accountId);
    void updateAccountBalance(int accountId, double newBalance);
    double getAccountBalance(int accountId); // Kept for direct balance check if needed
}
