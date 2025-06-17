package com.example.transaction.programmatic.h2.transactiondefinition;

import com.example.transaction.programmatic.common.model.Account;
import com.example.transaction.programmatic.common.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component("h2TransactionDefinitionDemo")
public class H2TransactionDefinitionDemo {

    private final PlatformTransactionManager transactionManager;
    private final AccountService accountService;

    @Autowired
    public H2TransactionDefinitionDemo(@Qualifier("h2TransactionManager") PlatformTransactionManager transactionManager,
                                       AccountService accountService) {
        this.transactionManager = transactionManager;
        this.accountService = accountService;
    }

    public void transferFundsSuccessfully(int fromAccountId, int toAccountId, double amount) {
        System.out.println("[H2 TxDef] Attempting successful transfer of " + amount + " from " + fromAccountId + " to " + toAccountId);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("h2TxDefSuccessTransfer");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // Optionally set isolation level, timeout, read-only status
        // def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        // def.setTimeout(30); // 30 seconds
        // def.setReadOnly(false);

        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            accountService.transferFunds(fromAccountId, toAccountId, amount);
            transactionManager.commit(status);
            System.out.println("[H2 TxDef] Transfer committed successfully.");
        } catch (Exception e) {
            transactionManager.rollback(status);
            System.err.println("[H2 TxDef] Transfer failed and rolled back: " + e.getMessage());
        }
    }

    public void transferFundsWithRollback(int fromAccountId, int toAccountId, double amountToCauseError) {
        System.out.println("[H2 TxDef] Attempting transfer of " + amountToCauseError + " from " + fromAccountId + " to " + toAccountId + " (expecting rollback)");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("h2TxDefRollbackTransfer");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // Example: Setting a specific isolation level for this transaction
        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        System.out.println("[H2 TxDef] Using isolation level: ISOLATION_SERIALIZABLE for this attempt.");

        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            accountService.transferFunds(fromAccountId, toAccountId, amountToCauseError);
            transactionManager.commit(status);
            System.out.println("[H2 TxDef] Transfer committed (this should not happen for error scenario).");
        } catch (Exception e) {
            transactionManager.rollback(status);
            System.err.println("[H2 TxDef] Transfer failed as expected and rolled back: " + e.getMessage());
        }
    }

    private static void printAccountDetails(AccountService service, int accountId, String prefix) {
        Account acc = service.getAccount(accountId);
        System.out.println(prefix + (acc != null ? acc.toString() : "Account " + accountId + " not found."));
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(com.example.transaction.programmatic.config.H2Config.class);
        H2TransactionDefinitionDemo demo = context.getBean("h2TransactionDefinitionDemo", H2TransactionDefinitionDemo.class);
        AccountService accountService = context.getBean(AccountService.class);

        System.out.println("--- H2 TransactionDefinition Demo ---");
        System.out.println("Initial account states:");
        printAccountDetails(accountService, 1, "[H2 TxDef Init]");
        printAccountDetails(accountService, 2, "[H2 TxDef Init]");
        printAccountDetails(accountService, 3, "[H2 TxDef Init]");

        demo.transferFundsSuccessfully(1, 2, 75.00); // Different amount
        System.out.println("Account states after successful transfer:");
        printAccountDetails(accountService, 1, "[H2 TxDef Success]");
        printAccountDetails(accountService, 2, "[H2 TxDef Success]");

        demo.transferFundsWithRollback(1, 3, 666.00); // amount 666.00 triggers error
        System.out.println("Account states after rollback attempt:");
        printAccountDetails(accountService, 1, "[H2 TxDef Rollback]");
        printAccountDetails(accountService, 3, "[H2 TxDef Rollback]");

        System.out.println("Final H2 account states (TransactionDefinition Demo):");
        printAccountDetails(accountService, 1, "[H2 TxDef Final]");
        printAccountDetails(accountService, 2, "[H2 TxDef Final]");
        printAccountDetails(accountService, 3, "[H2 TxDef Final]");

        context.close();
    }
}
