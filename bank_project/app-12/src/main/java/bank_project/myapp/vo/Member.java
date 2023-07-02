package bank_project.myapp.vo;

public class Member {

  private static int userId = 1;

  private int no;
  private String name;
  private String password;
  private String accNum;
  private int balance;
  private String bankName;

  public Member() {
    this.no = userId++;
  }

  public Member(String empty) {

  }

  public static Member getMemberOnlyPassword(String password) {
    Member m = new Member("");
    m.setPassword(password);
    return m;
  }

  public static Member getMemberOnlyAccNum(String accNum) {
    Member m = new Member("");
    m.setAccNum(accNum);
    return m;
  }




  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
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
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAccNum() {
    return this.accNum;
  }

  public void setAccNum(String accNum) {
    this.accNum = accNum;
  }

  public int getBalance() {
    return this.balance;
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
    return this.bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }


}

