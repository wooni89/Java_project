package bank_project.myapp.vo;

public class Banking {
  private int no;
  private String name;
  private String password;
  private String accNum;
  private int balance;
  private String bankName;


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
