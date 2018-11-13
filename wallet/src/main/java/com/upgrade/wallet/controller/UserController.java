package com.upgrade.wallet.controller;

import com.upgrade.wallet.entities.User;
import com.upgrade.wallet.repositories.UserRepository;
import com.upgrade.wallet.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * API end point to create a user and fetch users
 */
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

    @GetMapping("/api/user")
    public List<User> findUsers(){
        return (List<User>)userRepository.findAll();
    }

    @GetMapping("/api/user/{id}")
    public Optional<User> findUser(@PathVariable("id") int uid){
        return userRepository.findById(uid);
    }

    @PostMapping("/api/user")
    public User createUser(@RequestBody User user){

        return userRepository.save(user);
    }


}
