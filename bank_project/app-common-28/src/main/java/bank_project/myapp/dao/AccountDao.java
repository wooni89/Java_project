package bank_project.myapp.dao;

import java.util.List;
import bank_project.myapp.vo.Account;

public interface AccountDao {

  void insert(Account account);

  List<Account> list();

  Account findAccount(String accNum);

  Account findAccountPassword(String accNum, String pwd);

  Account update(Account account);

  boolean delete(Account account);
  
  Account deposit(Account account);
  
  Account withraw(Account account);
  
  Account transfer(Account account);

}
