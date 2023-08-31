package bank_project.myapp.dao;

import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Transaction;

import java.util.List;

public interface TransactionDao {

  void deposit(Transaction transaction);

  void withdraw(Transaction transaction);

  void transfer(Transaction from, Transaction to);

  List<Transaction> findAll();

  List<Transaction> findByAccountNumber(Account account);
}
