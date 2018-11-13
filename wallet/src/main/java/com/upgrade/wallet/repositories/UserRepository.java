package com.upgrade.wallet.repositories;

import com.upgrade.wallet.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{

}
