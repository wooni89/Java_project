package bank_project.myapp;

import bank_project.myapp.handler.MemberHandler;
import bank_project.util.Prompt;

public class App {

  public static void main(String[] args) {

    printTitle();

    printMenu();

    while (true) {
      String menuNo = Prompt.inputString("번호를 입력하세요 > ");
      if (menuNo.equals("0")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) { // 계좌개설
        MemberHandler.inputAccounts();
      } else if (menuNo.equals("2")) { // 입금
        MemberHandler.deposit();
      } else if (menuNo.equals("3")) { // 출금
        MemberHandler.withraw();
      } else if (menuNo.equals("4")) { // 계좌 비밀번호 변경
        MemberHandler.updateAccount();
      } else if (menuNo.equals("5")) { // 계좌정보확인
        MemberHandler.viewAccount();
      } else if (menuNo.equals("6")) { // 계좌삭제
        MemberHandler.deleteMember();
      } else if (menuNo.equals("7")) { // 등록 회원 목록조회
        MemberHandler.printAccounts();
      } else {
        System.out.println(menuNo);
      }
    }

    Prompt.close();

  }

  static void printMenu() {
    System.out.println("-----------------------------------------------------");
    System.out.println("1.계좌개설    | 2. 입금    | 3. 출금         | 4. 계좌 비밀번호 변경");
    System.out.println("5.계좌정보확인 | 6. 계좌삭제 | 7.등록회원 목록조회 | 8. 종료");
    System.out.println("-----------------------------------------------------");
  }

  static void printTitle() {

    System.out.println("은행 업무 프로그램");

  }


}
