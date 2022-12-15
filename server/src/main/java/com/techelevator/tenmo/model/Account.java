package com.techelevator.tenmo.model;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Account {
    private int accountID;
    private int userID;
   // @Positive(message = "The amount amount must be greater than 0.")
    private BigDecimal balance;

    public Account() {
    }

    public Account(int accountID, int userID, BigDecimal balance) {
        this.accountID = accountID;
        this.userID = userID;
        this.balance = balance;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", userID=" + userID +
                ", balance=" + balance +
                '}';
    }
}
