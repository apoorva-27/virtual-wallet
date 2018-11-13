package com.upgrade.wallet.repositories;

import com.upgrade.wallet.entities.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
}
