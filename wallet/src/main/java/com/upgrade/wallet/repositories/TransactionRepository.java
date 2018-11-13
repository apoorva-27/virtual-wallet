package com.upgrade.wallet.repositories;

import com.upgrade.wallet.entities.Transactions;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transactions, Integer> {
}
