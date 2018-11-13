package com.upgrade.wallet.WalletTest;

import com.upgrade.wallet.entities.Account;
import com.upgrade.wallet.entities.Transactions;
import com.upgrade.wallet.entities.User;
import com.upgrade.wallet.entities.Wallet;
import com.upgrade.wallet.exceptions.AccountDoesntExist;
import com.upgrade.wallet.exceptions.InsufficientFunds;
import com.upgrade.wallet.repositories.AccountRepository;
import com.upgrade.wallet.repositories.TransactionRepository;
import com.upgrade.wallet.repositories.UserRepository;
import com.upgrade.wallet.service.WalletService;
import com.upgrade.wallet.service.WalletServiceImpl;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests to run verify the functionality of the entities and the services in this app
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class WalletTest {

    @Mock
    AccountRepository accRepoMock;

    @Mock
    TransactionRepository tranRepoMock;

    @InjectMocks
    WalletServiceImpl wsImplMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testentities(){
        User u=new User();
        u.setFirstName("John");
        u.setLastName("Doe");
        u.setId(1);
        assertEquals("John",u.getFirstName());
        assertEquals("Doe",u.getLastName());
        assertEquals(null,u.getMyWallet());

        Wallet w=new Wallet();
        w.setId(1);
        w.setUser(u);
        u.setMyWallet(w);
        w.setAccounts(new ArrayList<>());
        assertEquals(1,w.getUser().getId());
        assertEquals(new ArrayList<>(),w.getAccounts());


        Account a=new Account();
        a.setId(2);
        a.setVersionID(1);
        a.setBalance(100.00f);
        a.setAccountWallet(w);
        a.setTransactionsList(new ArrayList<>());
        assertEquals(2, a.getId());
        assertEquals(100.00f,a.getBalance());
        assertEquals(1,a.getVersionID());

    }

    @Test
    public void testAccount(){

        List<Account> acc=new ArrayList<>();
        Account a=new Account();
        a.setId(2);
        a.setVersionID(1);
        a.setBalance(100.00f);
        acc.add(a);

        User u2=new User("Jane","Doe");
        Wallet w2=new Wallet(acc);
        u2.setMyWallet(w2);
        w2.setUser(u2);

        List<Transactions> tl=new ArrayList<>();
        Date d=new Date();
        tl.add(new Transactions(100.00f,d,10.5f,"Deposit",a));

        assertEquals(0,w2.getId());
        assertEquals(acc,w2.getAccounts());
        assertEquals(2,a.getId());
        assertEquals(null,a.getTransactionsList());
        a.setTransactionsList(tl);
        assertEquals(tl,w2.getAccounts().get(0).getTransactionsList());

        Transactions t2=new Transactions();
        Date d2=new Date();
        t2.setAccount(a);
        t2.setAmount(20.0f);
        t2.setId(2);
        t2.setOpeningBalance(120.0f);
        t2.setTimestamp(d2);
        t2.setTransactionType("Withdraw");
        tl.add(t2);

        assertEquals(tl.get(1),w2.getAccounts().get(0).getTransactionsList().get(1));

    }

    @Test(expected=InsufficientFunds.class)
    public void testInsufficentFunds() throws InsufficientFunds,AccountDoesntExist{
        User u3=new User("Jane","Doe");

        Account a=new Account();
        a.setId(3);
        a.setBalance(10.0f);
        a.setVersionID(1);
        List<Account> acc=new ArrayList<>();
        acc.add(a);

        Wallet w3=new Wallet(acc);
        u3.setMyWallet(w3);
        w3.setUser(u3);
        when(accRepoMock.findById(3)).thenReturn(java.util.Optional.ofNullable(a));

        wsImplMock.withdraw(3,20.0f,"Withdraw");
    }

    @Test(expected=AccountDoesntExist.class)
    public void testAccDoesntExist() throws InsufficientFunds,AccountDoesntExist{
        User u3=new User("Jane","Doe");

        Account a=new Account();
        a.setId(3);
        a.setBalance(10.0f);
        a.setVersionID(1);
        List<Account> acc=new ArrayList<>();
        acc.add(a);

        Wallet w3=new Wallet(acc);
        u3.setMyWallet(w3);
        w3.setUser(u3);
        when(accRepoMock.findById(4)).thenReturn(java.util.Optional.ofNullable(null));

        wsImplMock.withdraw(4,20.0f,"Deposit");
    }

    @Test
    public void testDeposit() throws InsufficientFunds,AccountDoesntExist {
        User u3=new User("Jane","Doe");

        Account a=new Account();
        a.setId(4);
        a.setBalance(10.0f);
        a.setVersionID(1);
        List<Account> acc=new ArrayList<>();
        acc.add(a);

        Wallet w3=new Wallet(acc);
        u3.setMyWallet(w3);
        w3.setUser(u3);
        when(accRepoMock.findById(4)).thenReturn(java.util.Optional.ofNullable(a));

        assertEquals(20.0f,wsImplMock.deposit(4,10.0f,"Deposit"));
    }

    @Test
    public void testWithdraw() throws InsufficientFunds,AccountDoesntExist {
        User u3=new User("Jane","Doe");

        Account a=new Account();
        a.setId(4);
        a.setBalance(100.0f);
        a.setVersionID(1);
        List<Account> acc=new ArrayList<>();
        acc.add(a);

        Wallet w3=new Wallet(acc);
        u3.setMyWallet(w3);
        w3.setUser(u3);
        when(accRepoMock.findById(4)).thenReturn(java.util.Optional.ofNullable(a));

        assertEquals(90.0f,wsImplMock.withdraw(4,10.0f,"Withdraw"));

        assertEquals(90.0f,wsImplMock.checkBalance(4));
        List<Transactions> tl=new ArrayList<>();
        tl.add(new Transactions(100.0f,new Date(),10.0f,"Withdraw",a));
        a.setTransactionsList(tl);
        assertEquals(tl,wsImplMock.history(4,5));
        assertEquals(tl,wsImplMock.history(4,1));
    }

    @Test
    public void testTransfer() throws InsufficientFunds,AccountDoesntExist {
        User u3=new User("Jane","Doe");

        Account a1=new Account();
        a1.setId(4);
        a1.setBalance(100.0f);
        a1.setVersionID(1);
        List<Account> acc=new ArrayList<>();
        acc.add(a1);

        Account a2=new Account();
        a2.setId(5);
        a2.setBalance(40.0f);
        a2.setVersionID(1);
        List<Account> acc2=new ArrayList<>();
        acc2.add(a2);

        Wallet w3=new Wallet(acc);
        u3.setMyWallet(w3);
        w3.setUser(u3);
        when(accRepoMock.findById(4)).thenReturn(java.util.Optional.ofNullable(a1));
        when(accRepoMock.findById(5)).thenReturn(java.util.Optional.ofNullable(a2));
        Transactions t=new Transactions(100.0f,new Date(),10.0f,"Transfer",a1);
        Transactions t3=new Transactions(80.0f,new Date(),10.0f,"Transfer",a1);
        Mockito.lenient().when(tranRepoMock.save(t)).thenReturn(t);
        Transactions t2=new Transactions(40.0f,new Date(),10.0f,"Transfer",a2);
        Mockito.lenient().when(tranRepoMock.save(t2)).thenReturn(t2);
        when(wsImplMock.withdraw(4,10.0f,"Withdraw")).thenReturn(90.0f);
        when(wsImplMock.deposit(5,10.0f,"Deposit")).thenReturn(50.0f);
        assertEquals(t3.getOpeningBalance(),wsImplMock.transfer(4,5,10.0f,"Transfer"));
    }


}
