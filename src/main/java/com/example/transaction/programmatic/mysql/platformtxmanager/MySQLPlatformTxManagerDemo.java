package com.example.transaction.programmatic.mysql.platformtxmanager;

import com.example.transaction.common.model.Account;
import com.example.transaction.common.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component("mysqlPlatformTxManagerDemo")
public class MySQLPlatformTxManagerDemo {

    private final PlatformTransactionManager transactionManager;
    private final AccountService accountService;

    @Autowired
    public MySQLPlatformTxManagerDemo(@Qualifier("mysqlTransactionManager") PlatformTransactionManager transactionManager,
                                      AccountService accountService) {
        this.transactionManager = transactionManager;
        this.accountService = accountService;
    }

    public void transferFundsSuccessfully(int fromAccountId, int toAccountId, double amount) {
        System.out.println("[MySQL PTM] Attempting successful transfer of " + amount + " from account " + fromAccountId + " to " + toAccountId);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("mysqlPtmSuccessTransfer");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            accountService.transferFunds(fromAccountId, toAccountId, amount);
            transactionManager.commit(status);
            System.out.println("[MySQL PTM] Transfer committed successfully.");
        } catch (Exception e) {
            transactionManager.rollback(status);
            System.err.println("[MySQL PTM] Transfer failed and rolled back: " + e.getMessage());
        }
    }

    public void transferFundsWithRollback(int fromAccountId, int toAccountId, double amountToCauseError) {
        System.out.println("[MySQL PTM] Attempting transfer of " + amountToCauseError + " from account " + fromAccountId + " to " + toAccountId + " (expecting rollback)");
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("mysqlPtmRollbackTransfer");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            accountService.transferFunds(fromAccountId, toAccountId, amountToCauseError);
            transactionManager.commit(status);
            System.out.println("[MySQL PTM] Transfer committed (this should not happen for error scenario).");
        } catch (Exception e) {
            transactionManager.rollback(status);
            System.err.println("[MySQL PTM] Transfer failed as expected and rolled back: " + e.getMessage());
        }
    }

    private static void printAccountDetails(AccountService service, int accountId, String prefix) {
        Account acc = service.getAccount(accountId);
        System.out.println(prefix + (acc != null ? acc.toString() : "Account " + accountId + " not found."));
    }

    public static void main(String[] args) {
        System.out.println("== Ensure MySQL server is running and mysql.properties is configured correctly. ==");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(com.example.transaction.config.MySQLConfig.class);
        MySQLPlatformTxManagerDemo demo = context.getBean("mysqlPlatformTxManagerDemo", MySQLPlatformTxManagerDemo.class);
        AccountService accountService = context.getBean(AccountService.class);

        System.out.println("--- MySQL PlatformTransactionManager Demo ---");

        // For MySQL, IDs might be 1, 2, 3 if schema was run fresh.
        // If schema was run multiple times, IDs could be different.
        // The sample data in schema-mysql.sql inserts 3 accounts. We'll assume they are 1, 2, 3 for demo.
        // A robust test would fetch accounts by name or have known IDs.
        // We'll use 1, 2, 3 as per schema-mysql.sql for this demo.

        System.out.println("Initial account states (MySQL - IDs may vary if schema re-run):");
        printAccountDetails(accountService, 1, "[MySQL PTM Init]");
        printAccountDetails(accountService, 2, "[MySQL PTM Init]");
        printAccountDetails(accountService, 3, "[MySQL PTM Init]");

        demo.transferFundsSuccessfully(1, 2, 50.00); // Different amount for MySQL demo
        System.out.println("Account states after successful transfer (MySQL):");
        printAccountDetails(accountService, 1, "[MySQL PTM Success]");
        printAccountDetails(accountService, 2, "[MySQL PTM Success]");

        demo.transferFundsWithRollback(1, 3, 666.00); // amount 666.00 triggers error
        System.out.println("Account states after rollback attempt (MySQL):");
        printAccountDetails(accountService, 1, "[MySQL PTM Rollback]");
        printAccountDetails(accountService, 3, "[MySQL PTM Rollback]");

        System.out.println("Final MySQL account states:");
        printAccountDetails(accountService, 1, "[MySQL PTM Final]");
        printAccountDetails(accountService, 2, "[MySQL PTM Final]");
        printAccountDetails(accountService, 3, "[MySQL PTM Final]");

        context.close();
    }
}
