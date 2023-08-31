package bank_project.myapp.vo;

import java.io.Serializable;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private int no;
    private Customer owner;
    private String password;
    private String accNum;
    private int balance;

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", owner=" + owner +
                ", password='" + password + '\'' +
                ", accNum='" + accNum + '\'' +
                ", balance=" + balance +
                '}';
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Account other = (Account) obj;
        return no == other.no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
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

}

