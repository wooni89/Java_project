package bank_project.myapp.dao;

import bank_project.myapp.vo.Account;

import java.util.List;

public interface AccountDao {

  void insert(Account account);

  List<Account> findAll();

  Account findAccount(String accNum);

  boolean findAccountAndPassword(Account account);

  Account findByAccountAndOwner(int account);

  String findMaxAccNum();

  Account update(Account account);

  Account delete(Account account);

  Account deposit(Account account);

}
