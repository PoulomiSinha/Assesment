package com.db.awmd.challenge.service;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.awmd.challenge.domain.Account;
import com.db.awmd.challenge.domain.TransactionInfo;
import com.db.awmd.challenge.domain.TransferMoneyDetails;
import com.db.awmd.challenge.exception.InsufficientBalanceException;
import com.db.awmd.challenge.repository.AccountsRepository;
import com.db.awmd.challenge.repository.TransactionRepository;

import lombok.Getter;

@Service
public class AccountsService {

  @Getter
  private final AccountsRepository accountsRepository;

  @Autowired
  public AccountsService(AccountsRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }
  
  @Autowired
  private TransactionRepository transactionRepository;
  
  @Autowired
  private EmailNotificationService emailNotificationService;

  public void createAccount(Account account) {
    this.accountsRepository.createAccount(account);
  }

  public Account getAccount(String accountId) {
    return this.accountsRepository.getAccount(accountId);
  }

  public TransactionInfo sendMoney(TransferMoneyDetails transferMoney) throws InsufficientBalanceException

	  {
	        String fromAccountNumber = transferMoney.getFromAccountNumber();
	        String toAccountNumber = transferMoney.getToAccountNumber();
	        BigDecimal amount = transferMoney.getAmount();
	        String transferAmount = String.valueOf(amount); 
	        Account fromAccount = accountsRepository.getAccount(fromAccountNumber);
	        Account toAccount = accountsRepository.getAccount(toAccountNumber);
	        if(fromAccount.getBalance().compareTo(BigDecimal.ZERO) == 1
	                && fromAccount.getBalance().compareTo(amount) == 1
	        ){
	            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
	            toAccount.setBalance(toAccount.getBalance().add(amount));
	            TransactionInfo transaction = transactionRepository.save(new TransactionInfo(0L, fromAccountNumber,toAccountNumber, amount,new Timestamp(System.currentTimeMillis())));
	            emailNotificationService.notifyAboutTransfer(fromAccount, transferAmount);
	            emailNotificationService.notifyAboutTransfer(toAccount, transferAmount);
	            return transaction;
	        }else {
	        	throw new InsufficientBalanceException("Money cannot be transferred from " + fromAccountNumber + " to " + toAccountNumber + " due to insufficient balance");
	        }
	        
	    }

}
 