package bank_project.myapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bank_project.myapp.vo.Account;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.DataSource;

public class MySQLAccountDao implements AccountDao {

  DataSource ds;
  public static BreadcrumbPrompt prompt;
  

  public MySQLAccountDao(DataSource ds) {
    this.ds = ds;
  }

  @Override
  public void insert(Account account) {
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
        "insert into myapp_account(name, account_number, password, bank_name, balance)"
            + " values(?,?,sha1(?),?,?)")) {
        stmt.setString(1, account.getName());
        stmt.setString(2, account.getAccNum());
        stmt.setString(3, account.getPassword());
        stmt.setString(4, account.getBankName());
        stmt.setInt(5, account.getBalance());
        
        stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public List<Account> list() {
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
        "select account_no, name, account_number, password, bank_name, balance"
            + " from myapp_account order by account_no");
        ResultSet rs = stmt.executeQuery()) {
  
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
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
            "select account_no, name, account_number, password, bank_name, balance"
                + " from myapp_account where account_number=?")) {
      
        stmt.setString(1, account.getAccNum());

      try (ResultSet rs = stmt.executeQuery()) {
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
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account findAccountPassword(Account param) {
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
            "select account_no, name, account_number, password, bank_name, balance"
                + " from myapp_account"
                + " where account_number=? and password=sha1(?)")) {
      
        stmt.setString(1, param.getAccNum());
        stmt.setString(2, param.getPassword());
        
        try (ResultSet rs = stmt.executeQuery()) {
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
        }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account update(Account account) {

    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
          "update myapp_account set name=?, password=sha1(?)"
              + " where account_number=?")) {
      
         stmt.setString(1, account.getName());
         stmt.setString(2, account.getPassword());
         stmt.setString(3, account.getAccNum());

         stmt.executeUpdate();

      return account;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public Account delete(Account account) {
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
          "delete from myapp_account where account_number=?")) {
      
         stmt.setString(1, account.getAccNum());
         stmt.executeUpdate();

      return account;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account deposit(Account account) {

    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
        "UPDATE myapp_account SET balance = +? WHERE account_number=?")) {
            
        stmt.setInt(1, account.getBalance());
        stmt.setString(2, account.getAccNum());
        stmt.executeUpdate();

        return account;
        
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}



  @Override
  public Account withraw(Account account) {

    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
            "UPDATE myapp_account SET balance=-? WHERE account_number=?")) {

        stmt.setInt(1, account.getBalance());
        stmt.setString(2, account.getAccNum());
        stmt.executeUpdate();

        return account;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account transfer(Account account) {
    try (PreparedStatement subtractBalance = ds.getConnection(false).prepareStatement(
            "UPDATE myapp_account SET balance = -? WHERE account_number=?");
         PreparedStatement addBalance = ds.getConnection(false).prepareStatement(
            "UPDATE myapp_account SET balance = +? WHERE account_number=?")) {
      
      // 송금하는 계좌에서 금액 차감
      subtractBalance.setInt(1, account.getBalance());
      subtractBalance.setString(2, account.getAccNum());
      subtractBalance.executeUpdate();

      // 수신하는 계좌에 금액 입금
      addBalance.setInt(1, account.getBalance());
      addBalance.setString(2, account.getAccNum());
      addBalance.executeUpdate();

      return account;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }




}
