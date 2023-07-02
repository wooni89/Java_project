package bank_project.myapp;


import bank_project.myapp.handler.BankingHandler;
import bank_project.myapp.handler.BoardHandler;
import bank_project.myapp.handler.Handler;
import bank_project.myapp.handler.MemberHandler;
import bank_project.util.ArrayList;
import bank_project.util.LinkedList;
import bank_project.util.MenuPrompt;

public class App {

  public static void main(String[] args) {

    MenuPrompt prompt = new MenuPrompt();

    prompt.appendBreadcrumb("메뉴", getMenu());

    Handler memberHandler = new MemberHandler(prompt, "계좌업무", new ArrayList());
    Handler bankingHandler = new BankingHandler(prompt, "은행서비스", new ArrayList());
    Handler boardHandler = new BoardHandler(prompt, "고객VOC", new LinkedList());

    printTitle();

    prompt.printMenu();

    loop: while (true) {
      String menuNo = prompt.inputMenu();
      switch (menuNo) {
        case "0":
          break loop;
        case "1":
          memberHandler.execute();
          break;
        case "2":
          bankingHandler.execute();
          break;
        case "3":
          boardHandler.execute();
          break;
      }
    }


    prompt.close();

  }


  static String getMenu() {

    StringBuilder menu = new StringBuilder();
    menu.append("[1] 계좌업무\n");
    menu.append("[2] 은행서비스\n");
    menu.append("[3] 고객VOC 게시판\n");
    menu.append("[0] 종료\n");
    return menu.toString();
  }

  static void printTitle() {

    System.out.println("은행 업무 프로그램");
    System.out.println("----------------------------------");

  }
}
