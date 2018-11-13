package com.upgrade.wallet.entities;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import static javax.persistence.DiscriminatorType.STRING;

/**
 * Represents a transaction of type Withdraw, Transfer or Deposit
 * Stores the id, opening balance, timestamp, amount and transaction type
 */
@Entity
public class Transactions implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private float openingBalance;

    private Date timestamp;

    private float amount;

    private String transactionType;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @ManyToOne(cascade = {CascadeType.ALL})
    private Account account;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Transactions() {
    }

    public Transactions(float openingBalance, Date timestamp, float amount, String transactionType, Account account) {
        this.openingBalance = openingBalance;
        this.timestamp = timestamp;
        this.amount = amount;
        this.transactionType = transactionType;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Float openingBalance) {
        this.openingBalance = openingBalance;
    }
}
