package bank_project.myapp.handler;

import bank_project.myapp.vo.Member;

public class MemberList {

  private final static int DEFAULT_SIZE = 50;
  public Member[] members = new Member[DEFAULT_SIZE];
  private int length = 0;

  public boolean add(Member m) {

    if (this.length == members.length) {
      increase();
      return false;
    }
    this.members[this.length++] = m;
    return true;
  }

  public void increase() {
    Member[] arr = new Member[members.length + (members.length >> 1)];
    for (int i = 0; i < members.length; i++) {
      arr[i] = members[i];
    }
    members = arr;
  }

  public Member[] list() {
    Member[] arr = new Member[this.length];

    for (int i = 0; i < this.length; i++) {
      arr[i] = this.members[i];
    }
    return arr;
  }

  public Member get(int no) {
    for (int i = 0; i < this.length; i++) {
      Member m = this.members[i];
      if (m.getNo() == no) {
        return m;
      }
    }
    return null;
  }

  public Member findAccountNumber(String accNum) {
    for (int i = 0; i < this.length; i++) {
      Member m = this.members[i];
      if (m.getAccNum().equals(accNum))
        return m;
    }
    return null;
  }

  public Member findPassword(String password) {
    for (int i = 0; i < this.length; i++) {
      Member m = this.members[i];
      if (m.getAccNum().equals(password))
        return m;
    }
    return null;
  }

  public Member findBankName(String bankName) {
    for (int i = 0; i < this.length; i++) {
      Member m = this.members[i];
      if (m.getAccNum().equals(bankName))
        return m;
    }
    return null;
  }



  public boolean delete(int no) {
    int deleteIndex = indexOf(no);
    if (deleteIndex == -1) {
      return false;
    }

    for (int i = deleteIndex; i < this.length - 1; i++) {
      this.members[i] = this.members[i + 1];
    }
    this.members[this.length--] = null;
    return true;
  }

  private int indexOf(int memberNo) {
    for (int i = 0; i < this.length; i++) {
      Member m = this.members[i];
      if (m.getNo() == memberNo) {
        return i;
      }
    }
    return -1;
  }

}
