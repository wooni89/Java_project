package bank_project.myapp.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Transaction implements Serializable {
    private int no;
    private Account acc_num; // 계좌번호
    private Timestamp createdDate; //거래날짜
    private String tradeType; // 입금, 출금 송금
    private int amount; // 거래금액
    String customer ; // 거래자명

    @Override
    public String toString() {
        return "Transaction{" +
                "no=" + no +
                ", acc_num=" + acc_num +
                ", createdDate=" + createdDate +
                ", tradeType='" + tradeType + '\'' +
                ", amount=" + amount +
                ", Customer='" + customer + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(no);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {

            return false;
        }
        Transaction other = (Transaction) obj;
        return no == other.no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Account getAcc_num() {
        return acc_num;
    }

    public void setAcc_num(Account acc_num) {
        this.acc_num = acc_num;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
