package com.example.transaction.programmatic.common.dao;

import com.example.transaction.programmatic.common.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createAccount(Account account) {
        // For H2, assuming ID is provided or for specific MySQL cases if ID is predetermined
        String sql = "INSERT INTO ACCOUNT (id, name, balance) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, account.getId(), account.getName(), account.getBalance());
    }

    // Specific method for MySQL auto-increment if needed, or adapt createAccount
    public Account createAccountMySQL(String name, double balance) {
        String sql = "INSERT INTO ACCOUNT (name, balance) VALUES (?, ?)";
        jdbcTemplate.update(sql, name, balance);
        // This is a simplified way; for actual generated ID, more complex logic is needed
        // like using KeyHolder, which is beyond simple demo for now.
        // We'll assume for demo purposes we can query by name if names are unique or handle IDs outside.
        // Or, the service layer will handle ID generation for H2 and rely on auto-gen for MySQL.
        // For now, let's stick to the interface, service will manage ID differences.
        return null; // Placeholder, service will handle actual object creation and ID retrieval
    }


    @Override
    public Account findAccountById(int accountId) {
        String sql = "SELECT id, name, balance FROM ACCOUNT WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{accountId}, new AccountRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null; // Or throw a custom not found exception
        }
    }

    @Override
    public void updateAccountBalance(int accountId, double newBalance) {
        String sql = "UPDATE ACCOUNT SET balance = ? WHERE id = ?";
        jdbcTemplate.update(sql, newBalance, accountId);
    }

    @Override
    public double getAccountBalance(int accountId) {
        String sql = "SELECT balance FROM ACCOUNT WHERE id = ?";
        Double balance = jdbcTemplate.queryForObject(sql, new Object[]{accountId}, Double.class);
        return balance != null ? balance : 0.0;
    }

    private static class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Account(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("balance")
            );
        }
    }
}
