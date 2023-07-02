package bank_project.myapp.handler;

import bank_project.myapp.vo.Member;
import bank_project.util.ArrayList;
import bank_project.util.Prompt;

public class BankingHandler implements Handler {

  private ArrayList list = new ArrayList();
  private Prompt prompt;
  private String title;


  public BankingHandler(Prompt prompt, String title) {
    this.prompt = prompt;
    this.title = title;
  }

  public void execute() {
    printMenu();

    while (true) {
      String menuNo = prompt.inputString(" %s > ", this.title);
      if (menuNo.equals("0")) {
        return;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        this.deposit();
      } else if (menuNo.equals("2")) {
        this.withraw();
      } else if (menuNo.equals("3")) {
        // this.viewBoard();
      } else if (menuNo.equals("4")) {
        // this.trnasection();
      } else if (menuNo.equals("5")) {
        // this.booroaw();
      } else {
        System.out.println("번호를 다시 입력해 주세");
      }
    }
  }

  private static void printMenu() {
    System.out.println("1. 입금");
    System.out.println("2. 출금");
    System.out.println("3. 송금");// 구현필요
    System.out.println("4. 거래내역");// 구현필요
    System.out.println("5. 대출"); // 구현필요
    System.out.println("0. 메인");
  }

  public void deposit() { // 입금

    String accnum = this.prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
    String pwd = this.prompt.inputString("비밀번호를 입력 하세요 : ");
    Member m = (Member) this.list.get(new Member(accnum, pwd));

    if (m == null) {
      System.out.println("해당 회원이 없습니다.");
      return;
    }
    if (!m.getAccNum().equals(accnum)) {
      System.out.println("해당 계좌번호의 회원이 없습니다.");
      return;
    }
    if (!m.getPassword().equals(pwd)) {
      System.out.println("비밀번호가 다릅니다.");
      return;
    }
    while (true) {
      int plus = this.prompt.inputInt("입금금액 : ");
      if (plus > 0) {
        m.getBalancePlus(plus);
        System.out.println("-------------------");
        System.out.printf("이름 : %s\n", m.getName());
        System.out.printf("은행명 : %s\n", m.getBankName());
        System.out.printf("입금 후 잔액 : %,d \n", m.getBalance());
        System.out.println("-------------------");
        break;
      } else if (plus == 0) {
        System.out.println("0 이하의 금액은 입금이 불가합니다.");
      }
    }
  }

  public void withraw() { // 출금
    while (true) {
      String accnum = this.prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
      String pwd = this.prompt.inputString("비밀번호를 입력 하세요 : ");
      Member m = (Member) this.list.get(new Member(accnum, pwd));

      if (m == null) {
        System.out.println("해당 계좌번호의 회원이 없습니다.");
        return;
      }
      if (!m.getAccNum().equals(accnum)) {
        System.out.println("해당 계좌번호의 회원이 없습니다.");
        return;
      }
      if (!m.getPassword().equals(pwd)) {
        System.out.println("비밀번호가 다릅니다.");
        return;
      }
      while (true) {
        int minus = this.prompt.inputInt("출금금액 : ");
        if (m.getBalance() < minus) {
          System.out.println("잔고가 부족하여 출금이 불가합니다.");
        } else if (minus == 0) {
          System.out.println("0원은 출금할 수 없습니다.");
        } else {
          m.getBalanceMinus(minus);
          System.out.println("-------------------");
          System.out.printf("이름 : %s\n", m.getName());
          System.out.printf("은행명 : %s\n", m.getBankName());
          System.out.printf("출금 후 잔액 : %,d \n", m.getBalance());
          System.out.println("-------------------");
          return;
        }
        continue;
      }
    }
  }

  public void Transaction() {

  }


}
