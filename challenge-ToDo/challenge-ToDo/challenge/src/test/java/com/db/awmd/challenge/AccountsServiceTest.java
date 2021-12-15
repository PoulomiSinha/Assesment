package com.db.awmd.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransferMoneyDetails;
import com.db.awmd.challenge.exception.DuplicateAccountIdException;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.db.awmd.challenge.service.AccountsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsServiceTest {

  @Autowired
  private AccountsService accountsService;

  @Test
  public void addAccount() throws Exception {
    Account account = new Account("Id-123");
    account.setBalance(new BigDecimal(1000));
    this.accountsService.createAccount(account);

    assertThat(this.accountsService.getAccount("Id-123")).isEqualTo(account);
  }

  @Test
  public void addAccount_failsOnDuplicateId() throws Exception {
    String uniqueId = "Id-" + System.currentTimeMillis();
    Account account = new Account(uniqueId);
    this.accountsService.createAccount(account);

    try {
      this.accountsService.createAccount(account);
      fail("Should have failed when adding duplicate account");
    } catch (DuplicateAccountIdException ex) {
      assertThat(ex.getMessage()).isEqualTo("Account id " + uniqueId + " already exists!");
    }

  }
  
  @Test
  public void sendMoneyTest() {
      Account account1 = new Account("ID-1001", new BigDecimal(50000));
      Account account2 = new Account("ID-2002", new BigDecimal(2000));
      accountsService.createAccount(account1);
      accountsService.createAccount(account2);

      TransferMoneyDetails transferMoney =
              new TransferMoneyDetails(
                      account1.getAccountId(),
                      account2.getAccountId(),
                      new BigDecimal(3000)
              );
      accountsService.sendMoney(transferMoney);
      assertThat(accountsService.getAccount(account1.getAccountId())
              .getBalance())
              .isEqualTo(new BigDecimal(47000));
      assertThat(accountsService.getAccount(account2.getAccountId())
              .getBalance())
              .isEqualTo(new BigDecimal(5000));

  }
  
  @Test
  public void sendMoney_failsOnInsufficientBalance() throws Exception {
	  Account account1 = new Account("ID-1111", new BigDecimal(1000));
      Account account2 = new Account("ID-2222", new BigDecimal(5000));
      accountsService.createAccount(account1);
      accountsService.createAccount(account2);
      String fromAccount = account1.getAccountId();
      String toAccount = account2.getAccountId();
      

      TransferMoneyDetails transferMoney = 
              new TransferMoneyDetails(
                      account1.getAccountId(),
                      account2.getAccountId(),
                      new BigDecimal(3000)
              );
      try {
    	  this.accountsService.sendMoney(transferMoney);
    	  fail("Should have failed due to insufficient balance");
      } catch (InsufficientBalanceException ex) {
    	  assertThat(ex.getMessage()).isEqualTo("Money cannot be transferred from " + fromAccount + " to " + toAccount + " due to insufficient balance");
      }
  }
  
  @Test
  public void sendMoney_failsOnNegetiveBalance() throws Exception {
	  Account account1 = new Account("ID-1212", new BigDecimal(-1000));
      Account account2 = new Account("ID-3333", new BigDecimal(5000));
      accountsService.createAccount(account1);
      accountsService.createAccount(account2);
      String fromAccount = account1.getAccountId();
      String toAccount = account2.getAccountId();
      

      TransferMoneyDetails transferMoney = 
              new TransferMoneyDetails(
                      account1.getAccountId(),
                      account2.getAccountId(),
                      new BigDecimal(100)
              );
      try {
    	  this.accountsService.sendMoney(transferMoney);
    	  fail("Should have failed due to insufficient balance");
      } catch (InsufficientBalanceException ex) {
    	  assertThat(ex.getMessage()).isEqualTo("Money cannot be transferred from " + fromAccount + " to " + toAccount + " due to insufficient balance");
      }
  }

 }



