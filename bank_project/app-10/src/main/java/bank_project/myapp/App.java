package bank_project.myapp;

import bank_project.myapp.handler.MemberHandler;
import bank_project.util.Prompt;

public class App {

  public static void main(String[] args) {

    printTitle();

    printMenu();

    while (true) {
      String menuNo = Prompt.inputStirng("메인> ");
      if (menuNo.equals("6")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) { // 계좌개설
        MemberHandler.inputAccounts();
      } else if (menuNo.equals("2")) { // 입금
        // MemberHandler.deposit();
      } else if (menuNo.equals("3")) { // 출금
        // MemberHandler.withraw();
      } else if (menuNo.equals("4")) { // 계좌정보변경
        MemberHandler.updateAccount();
      } else if (menuNo.equals("5")) { // 잔고확인
        MemberHandler.printAccounts();
      } else if (menuNo.equals("6")) {
        MemberHandler.deleteMember();
      } else {
        System.out.println(menuNo);
      }
    }

    Prompt.close();

  }

  static void printMenu() {
    System.out.println("1.계좌개설");
    System.out.println("2.입금");
    System.out.println("3.출금");
    System.out.println("4.계좌정보변경");
    System.out.println("5.잔고확인");
    System.out.println("6.계좌삭제");
    System.out.println("7.종료");
  }

  static void printTitle() {

    System.out.println("은행 고객관리 프로그램");
    System.out.println("-------------------------");

  }

  static boolean promptContinue() {
    String response = Prompt.inputStirng("계속 입력하실 겁니까? (Y/n) ");
    if (!"".equals(response) && !"Y".equalsIgnoreCase(response)) {
      return false;
    }
    return true;
  }

}
