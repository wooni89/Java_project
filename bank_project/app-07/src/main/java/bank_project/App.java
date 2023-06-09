package bank_project;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    final int MAX_SIZE = 5;
    int userId = 1;
    int length = 0;

    int[] no = new int[MAX_SIZE];
    String[] name = new String[MAX_SIZE];
    String[] password = new String[MAX_SIZE];
    int[] accNum = new int[MAX_SIZE];
    String[] bankName = { "신한은행", "국민은행", "기업은행" };
    int[] balance = new int[MAX_SIZE];

    printTitle();

    for (int i = 0; i < MAX_SIZE; i++) {
      inputAccounts(scanner, i, no, name, password, accNum, bankName, balance, userId++);
      length++;
      if (!promptContinue(scanner)) {
        break;
      }
    }

    printAccounts(length, no, name, accNum, bankName, balance);

    scanner.close();

  }

  static void printTitle() {

    System.out.println("은행 고객관리 프로그램");
    System.out.println("-------------------------");

  }

  static void inputAccounts(Scanner scanner, int i, int[] no, String[] name, String[] password,
      int[] accNum, String[] bankName, int[] balance, int userId) {

    bankName = new String[] { "신한은행", "국민은행", "기업은행" };

    System.out.print("이름 ? ");
    name[i] = scanner.next();

    System.out.print("비밀번호 ? ");
    password[i] = scanner.next();

    System.out.println("계좌번호를 입력하세요 ex) 00000000 ");
    accNum[i] = scanner.nextInt();

    loop: while (true) {
      System.out.println("은행을 선택하세요.");
      System.out.println("1. 신한은행 2.국민은행 3.기업은행");
      int num = scanner.nextInt();
      scanner.nextLine();

      switch (num) {
        case 1:
          bankName[i] = bankName[0];
          break loop;
        case 2:
          bankName[i] = bankName[1];
          break loop;
        case 3:
          bankName[i] = bankName[2];
          break loop;
        default:
          System.out.println("무효한 번호 입니다.");
      }
    }
    System.out.println("계좌개설을 축하드립니다.");
    balance[i] = 0;

    no[i] = userId;

  }

  static boolean promptContinue(Scanner scanner) {
    System.out.println("계속 입력하실 겁니까? (Y/N) ");
    String response = scanner.nextLine();
    if (!"".equals(response) && !"Y".equalsIgnoreCase(response)) {
      return false;
    }
    return true;
  }

  static void printAccounts(int length, int[] no, String[] name, int[] accNum, String[] bankName, int[] balance) {
    System.out.println("-----------------------------------------------");
    System.out.println("번호, 이름, 계좌번호, 은행명, 계좌잔액");
    System.out.println("------------------------------------------------");

    for (int i = 0; i < length; i++) {
      System.out.printf("%d, %s, %d, %s, $%d\n", no[i], name[i], accNum[i], bankName[i], balance[i]);
    }
  }
}
