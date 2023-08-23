package bank_project.myapp.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Customer implements Serializable {
  private static final long serialVersionUID = 1L;
  
  public static int customerNo = 1;
  private int no;
  private String name;
  private String email;
  private String password;
  private String phoneNumber;
  private Timestamp createdDate;
  
  
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
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getPhoneNumber() {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  public Timestamp getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }
  
  
}
