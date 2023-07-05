package bank_project.dao;

import java.util.List;
import bank_project.myapp.vo.Account;

public interface AccountDao {

  void insert(Account account);

  List<Account> list();

  Account findAccount(String accNum);

  Account findAccountPassword(String accNum, String pwd);

  int update(Account account);

  boolean delete(Account account);

}
