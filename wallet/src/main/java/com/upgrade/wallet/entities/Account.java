package com.upgrade.wallet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Represents an account of an user
 * Stores the account id, balance, transactions and wallet of the user
 */
@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private Float balance;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JsonIgnore
    private Wallet accountWallet;

    @OneToMany(mappedBy="account")
    @JsonIgnore
    private List<Transactions> transactionsList;

    @Version
    private int VersionID;

    public int getVersionID() {
        return VersionID;
    }

    public void setVersionID(int versionID) {
        VersionID = versionID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Account() {
    }

    public Account(Float balance, Wallet accountWallet, List<Transactions> transactionsList) {
        this.balance = balance;
        this.accountWallet = accountWallet;
        this.transactionsList = transactionsList;
    }

    public Wallet getAccountWallet() {
        return accountWallet;
    }

    public void setAccountWallet(Wallet accountWallet) {
        this.accountWallet = accountWallet;
    }

    @JsonIgnore
    public List<Transactions> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }
}
