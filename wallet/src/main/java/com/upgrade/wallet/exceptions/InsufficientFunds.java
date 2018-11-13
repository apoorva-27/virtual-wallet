package com.upgrade.wallet.exceptions;

/**
 * Exception thrown when there are insufficient funds and transaction cannot be completed
 */
public class InsufficientFunds extends Exception {

    public InsufficientFunds() {
        super("Insufficient funds, unable to complete transaction.");
    }
}
