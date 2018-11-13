package com.upgrade.wallet.comparator;

import java.util.Comparator;

import com.upgrade.wallet.entities.Transactions;

/**
 * Comparator to compare transactions on date
 */
public class TransactionComparator implements Comparator<Transactions>{

    @Override
    public int compare(Transactions t1, Transactions t2) {
        return t2.getTimestamp().compareTo(t1.getTimestamp());
    }
}
