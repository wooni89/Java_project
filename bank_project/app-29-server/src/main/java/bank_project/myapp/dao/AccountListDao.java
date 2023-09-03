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
    System.out.println("111111");
    account.setNo(Account.userId++);
    System.out.println("222222");

    this.list.add(account);
    System.out.println("333333");

    JsonDataHelper.saveJson(filename, list);
    System.out.println("444444");

  }

  @Override
  public List<Account> list() {
    return this.list;
  }

  @Override
  public Account findAccount(Account account) {
    for (int i = 0; i < this.list.size(); i++) {
      Account findby = list.get(i);
      if (account.getAccNum().equals(findby.getAccNum())) {
        return findby;
       }
    }
    return null;
  }

  @Override
  public Account findAccountPassword(Account account) {
    for (int i = 0; i < this.list.size(); i++) {
      Account item = list.get(i);
      if (item.getPassword().equals(account.getPassword())) {
        return item;
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
  public Account delete(Account account) {
    for (int i = 0; i < list.size(); i++) {
        if (list.get(i).equals(account)) {
          list.remove(i);
          JsonDataHelper.saveJson(filename, list);
          return account;
        }
    }
    return null;
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
