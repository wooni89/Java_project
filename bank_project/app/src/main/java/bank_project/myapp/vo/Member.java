package bank_project.myapp.vo;

import java.io.Serializable;

public class Member implements Serializable, CsvObject {

  private static final long serialVersionUID = 1L;

  public static int userId = 1;

  private int no;
  private String name;
  private String password;
  private String accNum;
  private int balance;
  private String bankName;


  public Member() {
    this.no = userId++;
  }


  public Member(String accNum) {
    this.accNum = accNum;
  }

  public Member(String accNum, String password) {
    this.accNum = accNum;
    this.password = password;
  }

  public static Member fromCsv(String csv) {

    String[] values = csv.split(",");
    Member member = new Member();
    member.setNo(Integer.parseInt(values[0]));
    member.setName(values[1]);
    member.setAccNum(values[2]);
    member.setPassword(values[3]);
    member.setBankName(values[4]);
    member.setBalance(Integer.parseInt(values[5]));


    if (Member.userId <= member.getNo()) {
      Member.userId = member.getNo() + 1;
    }

    return member;
  }

  @Override
  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%d", this.getNo(), this.getName(), this.getAccNum(),
        this.getPassword(), this.getBankName(), this.getBalance());
  }


  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }

    if (this.getClass() != obj.getClass()) {
      return false;
    }

    Member m = (Member) obj;

    if (!this.getAccNum().equals(m.getAccNum())) {
      return false;
    }

    if (!this.getPassword().equals(m.getPassword())) {
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

