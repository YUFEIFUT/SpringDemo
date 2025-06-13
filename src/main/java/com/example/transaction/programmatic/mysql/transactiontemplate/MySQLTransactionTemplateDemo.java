package com.example.transaction.programmatic.mysql.transactiontemplate;

import com.example.transaction.common.model.Account;
import com.example.transaction.common.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Component("mysqlTransactionTemplateDemo")
public class MySQLTransactionTemplateDemo {

    private final TransactionTemplate transactionTemplate;
    private final AccountService accountService;

    @Autowired
    public MySQLTransactionTemplateDemo(@Qualifier("mysqlTransactionTemplate") TransactionTemplate transactionTemplate,
                                        AccountService accountService) {
        this.transactionTemplate = transactionTemplate;
        this.accountService = accountService;
    }

    public void transferFundsSuccessfully(int fromAccountId, int toAccountId, double amount) {
        System.out.println("[MySQL TxTmpl] Attempting successful transfer of " + amount + " from " + fromAccountId + " to " + toAccountId);
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        accountService.transferFunds(fromAccountId, toAccountId, amount);
                        System.out.println("[MySQL TxTmpl] Transfer logic executed within transaction.");
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        System.err.println("[MySQL TxTmpl] Error during transfer, setting rollbackOnly: " + e.getMessage());
                        if (e instanceof RuntimeException) throw (RuntimeException)e; // Allow TransactionTemplate to handle rollback
                        throw new RuntimeException("Wrapped exception from transferFunds", e);
                    }
                }
            });
            System.out.println("[MySQL TxTmpl] Transaction committed successfully.");
        } catch (Exception e) {
             System.err.println("[MySQL TxTmpl] Transfer failed and rolled back (exception caught outside template execute): " + e.getMessage());
        }
    }

    public void transferFundsWithRollback(int fromAccountId, int toAccountId, double amountToCauseError) {
        System.out.println("[MySQL TxTmpl] Attempting transfer of " + amountToCauseError + " from " + fromAccountId + " to " + toAccountId + " (expecting rollback)");
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        accountService.transferFunds(fromAccountId, toAccountId, amountToCauseError);
                        System.out.println("[MySQL TxTmpl] Transfer logic executed (this part might not be reached for error).");
                    } catch (AccountService.InsufficientFundsException | IllegalArgumentException e) {
                        status.setRollbackOnly();
                        System.err.println("[MySQL TxTmpl] Business exception during transfer, setting rollbackOnly: " + e.getMessage());
                        throw e;
                    } catch (RuntimeException e) {
                        status.setRollbackOnly();
                        System.err.println("[MySQL TxTmpl] Runtime exception during transfer, setting rollbackOnly: " + e.getMessage());
                        throw e;
                    }
                }
            });
            System.out.println("[MySQL TxTmpl] Transaction committed (this should not happen for error scenario).");
        } catch (Exception e) {
            System.err.println("[MySQL TxTmpl] Transfer failed as expected and rolled back (exception caught outside template execute): " + e.getMessage());
        }
    }

    private static void printAccountDetails(AccountService service, int accountId, String prefix) {
        Account acc = service.getAccount(accountId);
        System.out.println(prefix + (acc != null ? acc.toString() : "Account " + accountId + " not found."));
    }

    public static void main(String[] args) {
        System.out.println("== Ensure MySQL server is running and mysql.properties is configured correctly. ==");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(com.example.transaction.config.MySQLConfig.class);
        MySQLTransactionTemplateDemo demo = context.getBean("mysqlTransactionTemplateDemo", MySQLTransactionTemplateDemo.class);
        AccountService accountService = context.getBean(AccountService.class);

        System.out.println("--- MySQL TransactionTemplate Demo ---");
        System.out.println("Initial account states (MySQL - IDs may vary):");
        printAccountDetails(accountService, 1, "[MySQL TxTmpl Init]");
        printAccountDetails(accountService, 2, "[MySQL TxTmpl Init]");
        printAccountDetails(accountService, 3, "[MySQL TxTmpl Init]");

        demo.transferFundsSuccessfully(1, 2, 15.00); // Different amount
        System.out.println("Account states after successful transfer (MySQL):");
        printAccountDetails(accountService, 1, "[MySQL TxTmpl Success]");
        printAccountDetails(accountService, 2, "[MySQL TxTmpl Success]");

        demo.transferFundsWithRollback(1, 3, 666.00); // amount 666.00 triggers error
        System.out.println("Account states after rollback attempt (MySQL):");
        printAccountDetails(accountService, 1, "[MySQL TxTmpl Rollback]");
        printAccountDetails(accountService, 3, "[MySQL TxTmpl Rollback]");

        System.out.println("Final MySQL account states (TransactionTemplate Demo):");
        printAccountDetails(accountService, 1, "[MySQL TxTmpl Final]");
        printAccountDetails(accountService, 2, "[MySQL TxTmpl Final]");
        printAccountDetails(accountService, 3, "[MySQL TxTmpl Final]");

        context.close();
    }
}
