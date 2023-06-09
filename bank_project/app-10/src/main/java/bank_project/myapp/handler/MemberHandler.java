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
    if (!available()) {
      System.out.println("더이상 입력할 수 없습니다.");
      return;
    }

    name[length] = Prompt.inputStirng("이름 ? ");
    password[length] = Prompt.inputStirng("비밀번호 ? ");
    accNum[length] = Prompt.inputInt("계좌번호를 입력하세요 ex) 00000000 ");

    loop: while (true) {
      System.out.println("은행을 선택하세요.");
      int num = Prompt.inputInt("1. 신한은행 2.국민은행 3.기업은행 \n");
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
      System.out.printf("%d, %s, %d, %s, $%d\n",
          no[i], name[i], accNum[i], bankName[i], balance[i]);
    }
  }

  public static void viewAccount() {
    String memberNo = Prompt.inputStirng("번호? ");
    for (int i = 0; i < length; i++) {
      if (no[i] == Integer.parseInt(memberNo)) {
        System.out.printf("이름 : %s\n", name[i]);
        System.out.printf("계좌번호 : %d\n", accNum[i]);
        System.out.printf("은행이름 : %s\n", bankName[i]);
        System.out.printf("잔고 : %d\n", balance[i]);
        return;
      }
    }
    System.out.println("해당 번호의 회원이 없습니다.");
  }

  public static void updateAccount() {
    String memberNo = Prompt.inputStirng("번호? ");
    for (int i = 0; i < length; i++) {
      if (no[i] == Integer.parseInt(memberNo)) {
        System.out.printf("이름(%s) :", name[i]);
        name[i] = Prompt.inputStirng("");
        System.out.printf("계좌번호(%d) :", accNum[i]);
        accNum[i] = Prompt.inputInt("");
        System.out.print("새 비밀번호 : ");
        password[i] = Prompt.inputStirng("");
        return;
      }
    }
  }

  public static void deleteMember() {
    int memberNo = Prompt.inputInt("번호? ");
    int deleteAccount = indexOf(memberNo);

    if (deleteAccount == -1) {
      System.out.println("해당 번호에 회원정보가 없습니다.");
      return;
    }

    for (int i = deleteAccount; i < length - 1; i++) {
      no[i] = no[i + 1];
      name[i] = name[i + 1];
      accNum[i] = accNum[i + 1];
      password[i] = password[i + 1];
      bankName[i] = bankName[i + 1];
      balance[i] = balance[i + 1];
    }
    length--;
  }

  private static int indexOf(int memberNo) {
    for (int i = 0; i < length; i++) {
      if (no[i] == memberNo) {
        return i;
      }
    }
    return -1;
  }
  // public static int deposit(int balanceDeposit) {

  // }

  // public static int withraw(int balanceWithraw) {

  // }

  private static boolean available() {
    return length < MAX_SIZE;
  }

}
