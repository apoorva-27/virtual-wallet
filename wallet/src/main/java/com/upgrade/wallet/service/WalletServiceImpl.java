package com.upgrade.wallet.service;

import com.upgrade.wallet.comparator.TransactionComparator;
import com.upgrade.wallet.entities.Account;
import com.upgrade.wallet.entities.Transactions;
import com.upgrade.wallet.entities.User;
import com.upgrade.wallet.entities.Wallet;
import com.upgrade.wallet.exceptions.AccountDoesntExist;
import com.upgrade.wallet.exceptions.InsufficientFunds;
import com.upgrade.wallet.repositories.AccountRepository;
import com.upgrade.wallet.repositories.TransactionRepository;
import com.upgrade.wallet.repositories.UserRepository;
import com.upgrade.wallet.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of all methods specified bu the Wallet Service
 */
@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Float checkBalance(int aid) {
        Optional<Account> a = accountRepository.findById(aid);
        if (a.isPresent()) {
            return a.get().getBalance();
        }
        return null;
    }

    @Override
    public Float withdraw(int aid, Float amount, String type) throws InsufficientFunds, AccountDoesntExist {


        Optional<Account> a = accountRepository.findById(aid);
        if (a.isPresent()) {
            Account acc = a.get();
            Float currentBalance = a.get().getBalance();
            if (currentBalance < amount) {
                throw new InsufficientFunds();
            }
            Float newBalance = currentBalance - amount;
            acc.setBalance(newBalance);
            accountRepository.save(acc);
            Transactions t = new Transactions(currentBalance, new Date(), amount, type, acc);
            transactionRepository.save(t);
            return newBalance;
        } else {
            throw new AccountDoesntExist();
        }
    }

    @Override
    public Float deposit(int aid, Float amount, String type) throws AccountDoesntExist {


        Optional<Account> a = accountRepository.findById(aid);
        if (a.isPresent()) {
            Account acc = a.get();
            Float currentBalance = a.get().getBalance();
            Float newBalance = currentBalance + amount;
            acc.setBalance(newBalance);
            accountRepository.save(acc);
            Transactions t = new Transactions(currentBalance, new Date(), amount, type, acc);
            transactionRepository.save(t);

            return newBalance;
        } else {
            throw new AccountDoesntExist();
        }

    }

    @Override
    public Float transfer(int fromAcc, int toAcc, Float amount, String type) throws InsufficientFunds, AccountDoesntExist {
        withdraw(fromAcc, amount, type);
        deposit(toAcc, amount, type);
        return checkBalance(fromAcc);
    }

    @Override
    public List<Transactions> history(int accId, int N) throws AccountDoesntExist {

        Optional<Account> a = accountRepository.findById(accId);
        if (a.isPresent()) {
            Account acc = a.get();
            List<Transactions> t = acc.getTransactionsList();
            Collections.sort(t, new TransactionComparator());
            if (N < t.size()) {
                return t.subList(0, N);
            } else {
                return t.subList(0, t.size());
            }
        } else {
            throw new AccountDoesntExist();
        }
    }

    @Override
    public Wallet createWallet(int uid) {
        Wallet w=new Wallet();
        Wallet wal= walletRepository.save(w);
        Optional<User> u=userRepository.findById(uid);
        if (u.isPresent()){
            User user=u.get();
            wal.setUser(user);
            user.setMyWallet(wal);

            userRepository.save(user);
            walletRepository.save(wal);
            return wal;
        }
        return null;
    }
}


