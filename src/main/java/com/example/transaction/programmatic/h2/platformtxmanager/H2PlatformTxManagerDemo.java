package com.example.transaction.programmatic.h2.platformtxmanager;

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

@Component("h2PlatformTxManagerDemo")
public class H2PlatformTxManagerDemo {

    private final PlatformTransactionManager transactionManager;
    private final AccountService accountService;

    @Autowired
    public H2PlatformTxManagerDemo(@Qualifier("h2TransactionManager") PlatformTransactionManager transactionManager,
                                   AccountService accountService) {
        this.transactionManager = transactionManager;
        this.accountService = accountService;
    }

    public void transferFundsSuccessfully(int fromAccountId, int toAccountId, double amount) {
        System.out.println("[H2 PTM] Attempting successful transfer of " + amount + " from account " + fromAccountId + " to " + toAccountId);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("h2PtmSuccessTransfer");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            accountService.transferFunds(fromAccountId, toAccountId, amount);
            transactionManager.commit(status);
            System.out.println("[H2 PTM] Transfer committed successfully.");
        } catch (Exception e) {
            transactionManager.rollback(status);
            System.err.println("[H2 PTM] Transfer failed and rolled back: " + e.getMessage());
        }
    }

    public void transferFundsWithRollback(int fromAccountId, int toAccountId, double amountToCauseError) {
        System.out.println("[H2 PTM] Attempting transfer of " + amountToCauseError + " from account " + fromAccountId + " to " + toAccountId + " (expecting rollback)");
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("h2PtmRollbackTransfer");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            // The AccountService's transferFunds method will throw an exception if amount is 666.00
            accountService.transferFunds(fromAccountId, toAccountId, amountToCauseError);
            transactionManager.commit(status); // Should not be reached if error occurs
            System.out.println("[H2 PTM] Transfer committed (this should not happen for error scenario).");
        } catch (Exception e) {
            transactionManager.rollback(status);
            System.err.println("[H2 PTM] Transfer failed as expected and rolled back: " + e.getMessage());
        }
    }

    private static void printAccountDetails(AccountService service, int accountId, String prefix) {
        Account acc = service.getAccount(accountId);
        System.out.println(prefix + (acc != null ? acc.toString() : "Account " + accountId + " not found."));
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(com.example.transaction.programmatic.config.H2Config.class);
        H2PlatformTxManagerDemo demo = context.getBean("h2PlatformTxManagerDemo", H2PlatformTxManagerDemo.class);
        AccountService accountService = context.getBean(AccountService.class);

        System.out.println("--- H2 PlatformTransactionManager Demo ---");

        // Initial state
        System.out.println("Initial account states:");
        printAccountDetails(accountService, 1, "[H2 PTM Init]");
        printAccountDetails(accountService, 2, "[H2 PTM Init]");
        printAccountDetails(accountService, 3, "[H2 PTM Init]");


        // Successful transfer
        demo.transferFundsSuccessfully(1, 2, 100.00);
        System.out.println("Account states after successful transfer:");
        printAccountDetails(accountService, 1, "[H2 PTM Success]");
        printAccountDetails(accountService, 2, "[H2 PTM Success]");

        // Transfer that should rollback (e.g. insufficient funds logic is in AccountService)
        // For PlatformTransactionManager, we rely on AccountService to throw exception.
        // Using amount 666.00 to trigger simulated error in AccountServiceImpl
        demo.transferFundsWithRollback(1, 3, 666.00);
        System.out.println("Account states after rollback attempt:");
        // Account 1 balance should be what it was before this rollback attempt
        // Account 3 balance should be unchanged.
        printAccountDetails(accountService, 1, "[H2 PTM Rollback]");
        printAccountDetails(accountService, 3, "[H2 PTM Rollback]");

        // Verify original balances if rollback occurred on an earlier failed transfer
        // E.g. if account 1 had 900 and account 3 had 2000.
        // After rollback of 666 from 1 to 3, account 1 should still be 900, account 3 still 2000.
        // (Assuming the 100 transfer from 1 to 2 made acc 1: 900, acc 2: 1600)
        // So, after successful (1->2, 100) and failed (1->3, 666):
        // Acc 1: 1000 - 100 = 900
        // Acc 2: 1500 + 100 = 1600
        // Acc 3: 2000 (no change from rollback)

        System.out.println("Final H2 account states:");
        printAccountDetails(accountService, 1, "[H2 PTM Final]");
        printAccountDetails(accountService, 2, "[H2 PTM Final]");
        printAccountDetails(accountService, 3, "[H2 PTM Final]");

        context.close();
    }
}
