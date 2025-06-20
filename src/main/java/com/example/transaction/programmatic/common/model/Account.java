package com.example.transaction.programmatic.common.model;

public class Account {
    private int id;
    private String name;
    private double balance;

    // Constructors
    public Account() {}

    public Account(int id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", name='" + name + "', balance=" + String.format("%.2f", balance) + "}";
    }
}
