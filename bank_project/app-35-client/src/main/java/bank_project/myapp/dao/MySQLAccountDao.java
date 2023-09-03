package bank_project.myapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bank_project.myapp.vo.Account;

public class MySQLAccountDao implements AccountDao {

  Connection con;

  public MySQLAccountDao(Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Account account) {
    try (Statement stmt = con.createStatement()) {

      stmt.executeUpdate(String.format(
          "insert into" + " myapp_account(name, account_number, password, bank_name, balance)"
              + " values('%s','%s','%s','%s', %d)",
          account.getName(), account.getAccNum(), account.getPassword(), account.getBankName(),
          account.getBalance()));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public List<Account> list() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select " + "account_no, name, account_number, password, bank_name, balance"
                + " from myapp_account" + " order by account_no")) {

      List<Account> list = new ArrayList<>();

      while (rs.next()) {
        Account account = new Account();
        account.setNo(rs.getInt("account_no"));
        account.setName(rs.getString("name"));
        account.setAccNum(rs.getString("account_number"));
        account.setPassword(rs.getString("password"));
        account.setBankName(rs.getString("bank_name"));
        account.setBalance(rs.getInt("balance"));

        list.add(account);
      }

      return list;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account findAccount(Account account) {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select " + "account_no, name, account_number, password, bank_name, balance"
                + " from myapp_account" + " where account_number=" + account)) {

      if (rs.next()) {
        Account acc = new Account();
        acc.setNo(rs.getInt("account_no"));
        acc.setName(rs.getString("name"));
        acc.setAccNum(rs.getString("account_number"));
        acc.setPassword(rs.getString("password"));
        acc.setBankName(rs.getString("bank_name"));
        acc.setBalance(rs.getInt("balance"));

        return account;
      }

      return null;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account findAccountPassword(Account account) {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(String.format(
            "select " + "account_no, name, account_number, password, bank_name, balance"
                + " from myapp_account" + " where account_number='%s'" + " and password='%s'",
            account.getAccNum(), account.getPassword()))) {

      if (rs.next()) {
        Account acc = new Account();
        acc.setNo(rs.getInt("account_no"));
        acc.setName(rs.getString("name"));
        acc.setAccNum(rs.getString("account_number"));
        acc.setPassword(rs.getString("password"));
        acc.setBankName(rs.getString("bank_name"));
        acc.setBalance(rs.getInt("balance"));

        return acc;
      }

      return null;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account update(Account account) {

    try (Statement stmt = con.createStatement()) {
      stmt.executeUpdate(String.format(
          "update myapp_account set" + " name='%s'," + " password='%s'"
              + " where account_number='%s'",
          account.getName(), account.getPassword(), account.getAccNum()));

      return account;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public Account delete(Account account) {
    try (Statement stmt = con.createStatement()) {
      stmt.executeUpdate(String.format(
          "delete from myapp_account where account_number='%s'",
          account.getAccNum()));

      return account;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account deposit(Account account) {
    
    if (account.getBalance() == 0) {
      System.out.println("0 또는 음수를 입력할 수 없습니다.");
      return null;
  }

    try (Statement stmt = con.createStatement()) {
        stmt.executeUpdate(String.format(
            "UPDATE myapp_account SET"
            + " balance = +%d"
            + " WHERE"
              + " account_number='%s'",
            account.getBalance(), account.getAccNum()
        ));
        
        

        return account;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}



  @Override
  public Account withraw(Account account) {
    
    if (account.getBalance() == 0) {
      System.out.println("0원은 출금할 수 없습니다.");
      return null;
    } 
    
    try (Statement stmt = con.createStatement()) {
      
      // DB에서 현재 계좌의 잔액을 조회하기 위한 쿼리 실행
      ResultSet rs = stmt.executeQuery(String.format(
        "SELECT balance FROM myapp_account WHERE account_number = '%s'",
        account.getAccNum()
      ));

      // 잔액이 조회되면 결과를 처리
      if (rs.next()) {
        int currentBalance = rs.getInt("balance");
        
        // 출금하려는 금액이 현재 잔액보다 클 경우 출금 불가
        if (account.getBalance() > currentBalance) {
          System.out.println("잔액이 부족합니다.");
          return null;
        }
        
        // 출금 가능한 경우 잔액 갱신
        stmt.executeUpdate(String.format(
          "UPDATE myapp_account SET"
          + " balance = -%d"
          + " WHERE"
          + " account_number = '%s'",
          account.getBalance(), account.getAccNum()
        ));
        
        return account;
      } else {
        System.out.println("존재하지 않는 계좌입니다.");
        return null;
      }
      
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

  @Override
  public Account transfer(Account account) {
    try (Statement stmt = con.createStatement()) {
        // 송금하는 계좌에서 금액 차감
        stmt.executeUpdate(String.format(
            "UPDATE myapp_account SET"
            + " balance = -%d"
            + " WHERE"
              + " account_number='%s'",
              account.getBalance(), account.getAccNum()
        ));
        
        // 수신하는 계좌에 금액 입금
        stmt.executeUpdate(String.format(
            "UPDATE myapp_account SET"
            + " balance = +%d"
            + " WHERE"
              + " account_number='%s'",
              account.getBalance(), account.getAccNum()
        ));

        return account;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}



}
