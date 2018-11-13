package com.upgrade.wallet.controller;

import com.upgrade.wallet.entities.Transactions;
import com.upgrade.wallet.entities.User;
import com.upgrade.wallet.entities.Wallet;
import com.upgrade.wallet.exceptions.AccountDoesntExist;
import com.upgrade.wallet.exceptions.InsufficientFunds;
import com.upgrade.wallet.repositories.UserRepository;
import com.upgrade.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * API end point to perform operations on accounts associated with a wallet
 */
@RestController
public class WalletController  {

    @Autowired
    WalletService walletService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/wallet/{wid}/balance/{aid}")
    public Float findBalance(@PathVariable("wid") int wid,
                                      @PathVariable("aid") int aid){
        return walletService.checkBalance(aid);
    }

    @PutMapping("/api/wallet/{wid}/withdraw/{aid}/amount/{amt}")
    public float withdraw(@PathVariable("wid") int wid,
                          @PathVariable("amt") Float amount,
                             @PathVariable("aid") int aid) throws InsufficientFunds,AccountDoesntExist{
        return walletService.withdraw(aid, amount,"Withdraw");
    }

    @PutMapping("/api/wallet/{wid}/deposit/{aid}/amount/{amt}")
    public float deposit(@PathVariable("wid") int wid,
                         @PathVariable("amt") Float amount,
                          @PathVariable("aid") int aid) throws AccountDoesntExist {
        return walletService.deposit(aid, amount,"Deposit");

    }

    @GetMapping("/api/wallet/{wid}/history/{aid}/N/{n}")
    public List<Transactions> history(@PathVariable("wid") int wid,
                                      @PathVariable("n") int N,
                                      @PathVariable("aid") int aid) throws AccountDoesntExist {
        return walletService.history(aid,N);
    }

    @PutMapping("/api/wallet/{wid}/from/{fromId}/to/{toId}/amount/{amt}")
    public float transfer(@PathVariable("fromId") int fromId,
                          @PathVariable("amt") Float amount,
                         @PathVariable("toId") int toId) throws InsufficientFunds, AccountDoesntExist {
        return walletService.transfer(fromId, toId, amount, "Transfer");
    }

    @PostMapping("/api/user/{uid}/wallet")
    public Wallet createWallet(@PathVariable("uid") int uid) {
        Optional<User> u=userRepository.findById(uid);
        if(u.isPresent()){
            User user=u.get();
            Wallet w=user.getMyWallet();
            if(w!=null){
                return w;
            }
            else {
                return walletService.createWallet(uid);
            }
        }
        else{
            return null;
        }

    }

}
