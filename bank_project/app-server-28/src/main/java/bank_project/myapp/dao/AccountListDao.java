package bank_project.myapp.dao;

import java.util.ArrayList;
import java.util.List;
import bank_project.myapp.vo.Account;
import bank_project.util.JsonDataHelper;

public class AccountListDao implements AccountDao {

  String filename;
  ArrayList<Account> list = new ArrayList<>();

  public AccountListDao(String filename) {
    this.filename = filename;
    JsonDataHelper.loadJson(filename, list, Account.class);
  }

  @Override
  public void insert(Account account) {
    account.setNo(Account.userId++);
    this.list.add(account);

    JsonDataHelper.saveJson(filename, list);
  }

  @Override
  public List<Account> list() {
    return this.list;
  }

  @Override
  public Account findAccount(String accNum) {
    for (int i = 0; i < this.list.size(); i++) {
      Account account = list.get(i);
      if (account.getAccNum().equals(accNum)) {
        return account;
      }
    }
    return null;
  }

  @Override
  public Account findAccountPassword(String accNum, String pwd) {
    for (int i = 0; i < this.list.size(); i++) {
      Account account = list.get(i);
      if (account.getAccNum().equals(accNum) && account.getPassword().equals(pwd)) {
        return account;
      }
    }
    return null;
  }

  @Override
  public Account update(Account account) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == account.getNo()) {
        list.set(i, account);
        JsonDataHelper.saveJson(filename, list);

      }
    }
    return account;
  }

  @Override
  public boolean delete(Account account) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).equals(account)) {
        list.remove(i);
        JsonDataHelper.saveJson(filename, list);
        return true;
      }
    }
    return false;
  }

  @Override
  public Account deposit(Account account) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == account.getNo()) {
        list.set(i, account);
        JsonDataHelper.saveJson(filename, list);

      }
    }
    return account;
  }

  @Override
  public Account withraw(Account account) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == account.getNo()) {
        list.set(i, account);
        JsonDataHelper.saveJson(filename, list);

      }
    }
    return account;
  }

  @Override
  public Account transfer(Account account) {
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getNo() == account.getNo()) {
        list.set(i, account);
        JsonDataHelper.saveJson(filename, list);

      }
    }
    return account;
  }
}
