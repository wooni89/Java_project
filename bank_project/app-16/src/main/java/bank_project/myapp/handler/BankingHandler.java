package bank_project.myapp.handler;

import bank_project.myapp.vo.Member;
import bank_project.util.List;
import bank_project.util.MenuPrompt;

public class BankingHandler implements Handler {

  private List list;
  private MenuPrompt prompt;
  private String title;

  public BankingHandler(MenuPrompt prompt, String title, List list) {
    this.prompt = prompt;
    this.title = title;
    this.list = list;
  }

  public void execute() {

    prompt.appendBreadcrumb(this.title, getMenu());

    prompt.printMenu();

    while (true) {
      String menuNo = prompt.inputMenu();
      switch (menuNo) {
        case "0":
          prompt.removeBreadcrumb();
          return;
        case "1":
          this.deposit();
          break;
        case "2":
          this.withraw();
          break;
        case "3":
          // this.viewBoard();
          break;
        case "4":
          // this.trnasection();
          break;
        case "5":
          // this.booroaw();
          break;
      }
    }
  }

  private static String getMenu() {
    StringBuilder menu = new StringBuilder();
    menu.append("[1] 입금\n");
    menu.append("[2] 출금\n");
    menu.append("[3] 송금\n");
    menu.append("[4] 거래내역\n");
    menu.append("[5] 대출\n");
    menu.append("[0] 메인메뉴 돌아가기\n");
    return menu.toString();

  }



  public void deposit() { // 입금

    String accnum = this.prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
    String pwd = this.prompt.inputString("비밀번호를 입력 하세요 : ");
    Member m = findMember(accnum, pwd);

    if (m == null) {
      System.out.println("해당 회원이 없습니다.");
      return;
    }
    // if (!m.getAccNum().equals(accnum)) {
    // System.out.println("해당 계좌번호의 회원이 없습니다.");
    // return;
    // }
    // if (!m.getPassword().equals(pwd)) {
    // System.out.println("비밀번호가 다릅니다.");
    // return;
    // }
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
      Member m = findMember(accnum, pwd);

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
          break;
        }
      }
    }
  }

  public void Transaction() {

  }

  private Member findMember(String accNum, String pwd) {
    for (int i = 0; i < this.list.size(); i++) {
      Member member = (Member) list.get(i);
      if (member.getAccNum().equals(accNum) && member.getPassword().equals(pwd)) {
        return member;
      }
    }
    return null;
  }


}
