package com.upgrade.wallet.repositories;

import com.upgrade.wallet.entities.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Integer> {

}
