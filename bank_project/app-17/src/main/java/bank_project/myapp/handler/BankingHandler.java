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


  }

  public void withraw() { // 출금

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
