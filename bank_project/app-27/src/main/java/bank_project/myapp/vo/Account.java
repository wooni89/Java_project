package bank_project.myapp.vo;

import java.io.Serializable;

public class Account implements Serializable, CsvObject, AutoIncrement {

  private static final long serialVersionUID = 1L;

  public static int userId = 1;

  private int no;
  private String name;
  private String password;
  private String accNum;
  private int balance;
  private String bankName;


  public Account() {}


  public Account(String accNum) {
    this.accNum = accNum;
  }

  public Account(String accNum, String password) {
    this.accNum = accNum;
    this.password = password;
  }

  public static Account fromCsv(String csv) {

    String[] values = csv.split(",");
    Account account = new Account();
    account.setNo(Integer.parseInt(values[0]));
    account.setName(values[1]);
    account.setAccNum(values[2]);
    account.setPassword(values[3]);
    account.setBankName(values[4]);
    account.setBalance(Integer.parseInt(values[5]));


    if (Account.userId <= account.getNo()) {
      Account.userId = account.getNo() + 1;
    }

    return account;
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%d", this.getNo(), this.getName(), this.getAccNum(),
        this.getPassword(), this.getBankName(), this.getBalance());
  }

  @Override
  public void updateKey() {
    if (Account.userId <= this.no) {
      Account.userId = this.no + 1;
    }

  }

  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }

    if (this.getClass() != obj.getClass()) {
      return false;
    }

    Account account = (Account) obj;

    if (!this.getAccNum().equals(account.getAccNum())) {
      return false;
    }

    if (!this.getPassword().equals(account.getPassword())) {
      return false;
    }
    return true;

  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAccNum() {
    return accNum;
  }

  public void setAccNum(String accNum) {
    this.accNum = accNum;
  }

  public int getBalance() {
    return balance;
  }

  public void getBalancePlus(int plus) {

    this.balance += plus;
  }

  public void getBalanceMinus(int minus) {
    this.balance -= minus;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }


}

