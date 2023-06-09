package bank_project;

import java.util.Scanner;

public class App {

  static Scanner scanner = new Scanner(System.in);

  static final int MAX_SIZE = 5;
  static int[] no = new int[MAX_SIZE];
  static String[] name = new String[MAX_SIZE];
  static String[] password = new String[MAX_SIZE];
  static int[] accNum = new int[MAX_SIZE];
  static String[] bankName = { "신한은행", "국민은행", "기업은행" };
  static int[] balance = new int[MAX_SIZE];
  static int userId = 1;
  static int length = 0;

  public static void main(String[] args) {

    printTitle();

    while (length < MAX_SIZE) {
      inputAccounts();
      if (!promptContinue(scanner)) {
        break;
      }
    }

    printAccounts();

    scanner.close();

  }

  static void printTitle() {

    System.out.println("은행 고객관리 프로그램");
    System.out.println("-------------------------");

  }

  static void inputAccounts() {

    name[length] = prompt("이름 ? ");
    password[length] = prompt("비밀번호 ? ");
    accNum[length] = promptInt("계좌번호를 입력하세요 ex) 00000000 ");

    loop: while (true) {
      prompt("은행을 선택하세요.");
      int num = promptInt("1. 신한은행 2.국민은행 3.기업은행");
      scanner.nextLine();

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

  static boolean promptContinue(Scanner scanner) {
    String response = prompt("계속 입력하실 겁니까? (Y/N) ");
    if (!"".equals(response) && !"Y".equalsIgnoreCase(response)) {
      return false;
    }
    return true;
  }

  static void printAccounts() {
    System.out.println("-----------------------------------------------");
    System.out.println("번호, 이름, 계좌번호, 은행명, 계좌잔액");
    System.out.println("------------------------------------------------");

    for (int i = 0; i < length; i++) {
      System.out.printf("%d, %s, %d, %s, $%d\n", no[i], name[i], accNum[i], bankName[i], balance[i]);
    }
  }

  static String prompt(String title) {
    System.out.print(title);
    return scanner.nextLine();
  }

  static int promptInt(String titleInt) {
    System.out.println(titleInt);
    return scanner.nextInt();
  }
}
