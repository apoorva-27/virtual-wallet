package com.upgrade.wallet.exceptions;

/**
 * Exception thrown when an account cannot be found
 */
public class AccountDoesntExist extends Exception {

    public AccountDoesntExist() {
        super("Unable to find account number. Please try again.");
    }
}
