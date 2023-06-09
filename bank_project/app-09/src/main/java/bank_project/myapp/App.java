package bank_project.myapp;

import bank_project.myapp.handler.MemberHandler;
import bank_project.util.Prompt;

public class App {

  public static void main(String[] args) {

    printTitle();

    while (MemberHandler.available()) {
      MemberHandler.inputAccounts();
      if (!promptContinue()) {
        break;
      }
    }

    MemberHandler.printAccounts();

    Prompt.close();

  }

  static void printTitle() {

    System.out.println("은행 고객관리 프로그램");
    System.out.println("-------------------------");

  }

  static boolean promptContinue() {
    String response = Prompt.inputStirng("계속 입력하실 겁니까? (Y/N) ");
    if (!"".equals(response) && !"Y".equalsIgnoreCase(response)) {
      return false;
    }
    return true;
  }

}
