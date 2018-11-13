package com.upgrade.wallet.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * Represents wallet, stores the id, accounts and user of the wallet
 */
@Entity
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy="accountWallet")
    private List<Account> accounts;

    @OneToOne
    private User user;

    public Wallet() {
    }

    public Wallet(List<Account> accounts) {
        this.accounts = accounts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
