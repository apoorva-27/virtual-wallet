package com.upgrade.wallet.service;

import com.upgrade.wallet.entities.Transactions;
import com.upgrade.wallet.entities.Wallet;
import com.upgrade.wallet.exceptions.AccountDoesntExist;
import com.upgrade.wallet.exceptions.InsufficientFunds;

import java.util.List;

/**
 * Interface represents all the operations required to be implemented for wallet operations
 */
public interface WalletService {

    public Float checkBalance(int accId);

    public Float withdraw(int accId, Float amount, String type) throws InsufficientFunds,AccountDoesntExist;

    public Float deposit(int accId,Float amount, String type) throws AccountDoesntExist;

    public Float transfer(int fromAcc, int toAcc, Float amount, String type) throws InsufficientFunds,AccountDoesntExist;

    public List<Transactions> history(int accId, int N) throws AccountDoesntExist;

    public Wallet createWallet(int uid);
}
