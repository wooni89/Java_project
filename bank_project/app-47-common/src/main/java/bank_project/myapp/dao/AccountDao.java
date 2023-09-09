package bank_project.myapp.dao;

import bank_project.myapp.vo.Account;

import java.util.List;

public interface AccountDao {

  void insert(Account account);

  List<Account> findAll();

  Account findAccount(String accNum);

  Account findByAccountAndOwner(int account);

  String findMaxAccNum();

  int update(Account account);

  int delete(Account account);

  int deposit(Account account, int amount);

  int withdraw(Account account, int amount);

}
