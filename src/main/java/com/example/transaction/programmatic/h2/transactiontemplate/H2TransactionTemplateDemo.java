package com.example.transaction.programmatic.h2.transactiontemplate;

import com.example.transaction.common.model.Account;
import com.example.transaction.common.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Component("h2TransactionTemplateDemo")
public class H2TransactionTemplateDemo {

    private final TransactionTemplate transactionTemplate;
    private final AccountService accountService;

    @Autowired
    public H2TransactionTemplateDemo(@Qualifier("h2TransactionTemplate") TransactionTemplate transactionTemplate,
                                     AccountService accountService) {
        this.transactionTemplate = transactionTemplate;
        this.accountService = accountService;
    }

    public void transferFundsSuccessfully(int fromAccountId, int toAccountId, double amount) {
        System.out.println("[H2 TxTmpl] Attempting successful transfer of " + amount + " from " + fromAccountId + " to " + toAccountId);
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        accountService.transferFunds(fromAccountId, toAccountId, amount);
                        System.out.println("[H2 TxTmpl] Transfer logic executed within transaction.");
                    } catch (Exception e) {
                        // It's important to rethrow the exception if we want TransactionTemplate to roll back
                        // or call status.setRollbackOnly() explicitly
                        status.setRollbackOnly();
                        System.err.println("[H2 TxTmpl] Error during transfer, setting rollbackOnly: " + e.getMessage());
                        // Re-throwing allows the outer catch block to also see it if needed.
                        // Or, ensure the service throws a runtime exception TransactionTemplate will catch.
                        // If AccountService.InsufficientFundsException is a RuntimeException, it should trigger rollback.
                        if (e instanceof RuntimeException) throw (RuntimeException)e;
                        throw new RuntimeException("Wrapped exception from transferFunds", e);
                    }
                }
            });
            System.out.println("[H2 TxTmpl] Transaction committed successfully.");
        } catch (Exception e) {
            // This catch block will catch exceptions from doInTransactionWithoutResult if they are re-thrown
            // or if TransactionTemplate itself throws an exception (e.g., due to rollback).
            System.err.println("[H2 TxTmpl] Transfer failed and rolled back (exception caught outside template execute): " + e.getMessage());
        }
    }

    public void transferFundsWithRollback(int fromAccountId, int toAccountId, double amountToCauseError) {
        System.out.println("[H2 TxTmpl] Attempting transfer of " + amountToCauseError + " from " + fromAccountId + " to " + toAccountId + " (expecting rollback)");
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        accountService.transferFunds(fromAccountId, toAccountId, amountToCauseError);
                         // Should not be reached if amountToCauseError triggers an exception in accountService
                        System.out.println("[H2 TxTmpl] Transfer logic executed (this part might not be reached for error).");
                    } catch (AccountService.InsufficientFundsException | IllegalArgumentException e) {
                        status.setRollbackOnly(); // Mark for rollback
                        System.err.println("[H2 TxTmpl] Business exception during transfer, setting rollbackOnly: " + e.getMessage());
                        throw e; // Re-throw to be caught by outer catch
                    } catch (RuntimeException e) {
                        status.setRollbackOnly(); // Mark for rollback
                        System.err.println("[H2 TxTmpl] Runtime exception during transfer, setting rollbackOnly: " + e.getMessage());
                        throw e; // Re-throw to be caught by outer catch
                    }
                }
            });
            System.out.println("[H2 TxTmpl] Transaction committed (this should not happen for error scenario).");
        } catch (Exception e) {
            System.err.println("[H2 TxTmpl] Transfer failed as expected and rolled back (exception caught outside template execute): " + e.getMessage());
        }
    }

    private static void printAccountDetails(AccountService service, int accountId, String prefix) {
        Account acc = service.getAccount(accountId);
        System.out.println(prefix + (acc != null ? acc.toString() : "Account " + accountId + " not found."));
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(com.example.transaction.config.H2Config.class);
        H2TransactionTemplateDemo demo = context.getBean("h2TransactionTemplateDemo", H2TransactionTemplateDemo.class);
        AccountService accountService = context.getBean(AccountService.class);

        System.out.println("--- H2 TransactionTemplate Demo ---");
        System.out.println("Initial account states:");
        printAccountDetails(accountService, 1, "[H2 TxTmpl Init]");
        printAccountDetails(accountService, 2, "[H2 TxTmpl Init]");
        printAccountDetails(accountService, 3, "[H2 TxTmpl Init]");

        demo.transferFundsSuccessfully(1, 2, 25.00); // Different amount
        System.out.println("Account states after successful transfer:");
        printAccountDetails(accountService, 1, "[H2 TxTmpl Success]");
        printAccountDetails(accountService, 2, "[H2 TxTmpl Success]");

        demo.transferFundsWithRollback(1, 3, 666.00); // amount 666.00 triggers error in AccountService
        System.out.println("Account states after rollback attempt:");
        printAccountDetails(accountService, 1, "[H2 TxTmpl Rollback]");
        printAccountDetails(accountService, 3, "[H2 TxTmpl Rollback]");

        System.out.println("Final H2 account states (TransactionTemplate Demo):");
        printAccountDetails(accountService, 1, "[H2 TxTmpl Final]");
        printAccountDetails(accountService, 2, "[H2 TxTmpl Final]");
        printAccountDetails(accountService, 3, "[H2 TxTmpl Final]");

        context.close();
    }
}
