package bank_project.myapp;

import bank_project.myapp.handler.BankingHandler;
import bank_project.myapp.handler.BoardHandler;
import bank_project.myapp.handler.MemberHandler;
import bank_project.util.Prompt;

public class App {

  public static void main(String[] args) {

    Prompt prompt = new Prompt();
    MemberHandler memberHandler = new MemberHandler(prompt, "계좌업무");
    BankingHandler bankingHandler = new BankingHandler(prompt, "은행서비스");
    BoardHandler boardHandler = new BoardHandler(prompt, "고객VOC");

    printTitle();

    printMenu();

    while (true) {
      String menuNo = prompt.inputString("번호를 입력하세요 > ");
      if (menuNo.equals("0")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) { // 계좌업무
        memberHandler.execute();
      } else if (menuNo.equals("2")) { // 은행서비스
        bankingHandler.execute();
      } else if (menuNo.equals("3")) { // 고객 VOC
        boardHandler.execute();
      } else {
        System.out.println(menuNo);
      }
    }

    prompt.close();

  }

  static void printMenu() {
    System.out.println("-----------------------------------------------------");
    System.out.println("[1] 계좌업무");
    System.out.println("[2] 은행서비스");
    System.out.println("[3] 고객VOC 게시판");
    System.out.println("[0] 종료");
    System.out.println("-----------------------------------------------------");
  }

  static void printTitle() {

    System.out.println("은행 업무 프로그램");

  }
}
