package com.example.transaction.programmatic.mysql.transactiondefinition;

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

@Component("mysqlTransactionDefinitionDemo")
public class MySQLTransactionDefinitionDemo {

    private final PlatformTransactionManager transactionManager;
    private final AccountService accountService;

    @Autowired
    public MySQLTransactionDefinitionDemo(@Qualifier("mysqlTransactionManager") PlatformTransactionManager transactionManager,
                                              AccountService accountService) {
        this.transactionManager = transactionManager;
        this.accountService = accountService;
    }

    public void transferFundsSuccessfully(int fromAccountId, int toAccountId, double amount) {
        System.out.println("[MySQL TxDef] Attempting successful transfer of " + amount + " from " + fromAccountId + " to " + toAccountId);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("mysqlTxDefSuccessTransfer");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // def.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ); // MySQL default
        // def.setTimeout(60);

        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            accountService.transferFunds(fromAccountId, toAccountId, amount);
            transactionManager.commit(status);
            System.out.println("[MySQL TxDef] Transfer committed successfully.");
        } catch (Exception e) {
            transactionManager.rollback(status);
            System.err.println("[MySQL TxDef] Transfer failed and rolled back: " + e.getMessage());
        }
    }

    public void transferFundsWithRollback(int fromAccountId, int toAccountId, double amountToCauseError) {
        System.out.println("[MySQL TxDef] Attempting transfer of " + amountToCauseError + " from " + fromAccountId + " to " + toAccountId + " (expecting rollback)");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("mysqlTxDefRollbackTransfer");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // Example: Mark as read-only (though this transfer operation is not read-only)
        // def.setReadOnly(true); // This would likely cause issues with updates if enforced strictly by DB/driver

        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            accountService.transferFunds(fromAccountId, toAccountId, amountToCauseError);
            transactionManager.commit(status);
            System.out.println("[MySQL TxDef] Transfer committed (this should not happen for error scenario).");
        } catch (Exception e) {
            transactionManager.rollback(status);
            System.err.println("[MySQL TxDef] Transfer failed as expected and rolled back: " + e.getMessage());
        }
    }

    private static void printAccountDetails(AccountService service, int accountId, String prefix) {
        Account acc = service.getAccount(accountId);
        System.out.println(prefix + (acc != null ? acc.toString() : "Account " + accountId + " not found."));
    }

    public static void main(String[] args) {
        System.out.println("== Ensure MySQL server is running and mysql.properties is configured correctly. ==");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(com.example.transaction.config.MySQLConfig.class);
        MySQLTransactionDefinitionDemo demo = context.getBean("mysqlTransactionDefinitionDemo", MySQLTransactionDefinitionDemo.class);
        AccountService accountService = context.getBean(AccountService.class);

        System.out.println("--- MySQL TransactionDefinition Demo ---");
        System.out.println("Initial account states (MySQL - IDs may vary):");
        printAccountDetails(accountService, 1, "[MySQL TxDef Init]");
        printAccountDetails(accountService, 2, "[MySQL TxDef Init]");
        printAccountDetails(accountService, 3, "[MySQL TxDef Init]");

        demo.transferFundsSuccessfully(1, 2, 30.00); // Different amount
        System.out.println("Account states after successful transfer (MySQL):");
        printAccountDetails(accountService, 1, "[MySQL TxDef Success]");
        printAccountDetails(accountService, 2, "[MySQL TxDef Success]");

        demo.transferFundsWithRollback(1, 3, 666.00); // amount 666.00 triggers error
        System.out.println("Account states after rollback attempt (MySQL):");
        printAccountDetails(accountService, 1, "[MySQL TxDef Rollback]");
        printAccountDetails(accountService, 3, "[MySQL TxDef Rollback]");

        System.out.println("Final MySQL account states (TransactionDefinition Demo):");
        printAccountDetails(accountService, 1, "[MySQL TxDef Final]");
        printAccountDetails(accountService, 2, "[MySQL TxDef Final]");
        printAccountDetails(accountService, 3, "[MySQL TxDef Final]");

        context.close();
    }
}
