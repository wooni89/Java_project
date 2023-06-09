package bank_project.myapp.handler;

import bank_project.util.Prompt;

public class MemberHandler {

  static final int MAX_SIZE = 5;
  static int[] no = new int[MAX_SIZE];
  static String[] name = new String[MAX_SIZE];
  static String[] password = new String[MAX_SIZE];
  static int[] accNum = new int[MAX_SIZE];
  static String[] bankName = { "신한은행", "국민은행", "기업은행" };
  static int[] balance = new int[MAX_SIZE];
  static int userId = 1;
  static int length = 0;

  public static void inputAccounts() {

    name[length] = Prompt.inputStirng("이름 ? ");
    password[length] = Prompt.inputStirng("비밀번호 ? ");
    accNum[length] = Prompt.inputIntStirng("계좌번호를 입력하세요 ex) 00000000 ");

    loop: while (true) {
      Prompt.inputStirng("은행을 선택하세요.");
      int num = Prompt.inputIntStirng("1. 신한은행 2.국민은행 3.기업은행");

      switch (num) {
        case 1:
          bankName[length] = bankName[0];
          break loop;
        case 2:
          bankName[length] = bankName[1];
          break loop;
        case 3:
          bankName[length] = bankName[2];
          break loop;
        default:
          System.out.println("무효한 번호 입니다.");
      }
    }
    System.out.println("계좌개설을 축하드립니다.");
    balance[length] = 0;

    no[length] = userId;
    length++;

  }

  public static void printAccounts() {
    System.out.println("-----------------------------------------------");
    System.out.println("번호, 이름, 계좌번호, 은행명, 계좌잔액");
    System.out.println("------------------------------------------------");

    for (int i = 0; i < length; i++) {
      System.out.printf("%d, %s, %d, %s, $%d\n", no[i], name[i], accNum[i], bankName[i], balance[i]);
    }
  }

  public static boolean available() {
    return length < MAX_SIZE;
  }

}
