package bank_project;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    System.out.println("은행 회원관리 프로그램");
    System.out.println("-------------------------");

    final int SIZE = 1;
    int userId = 1;
    int length = 0;

    int[] no = new int[SIZE];
    String[] name = new String[SIZE];
    String[] password = new String[SIZE];
    int[] accNum = new int[SIZE];
    String[] bankName = {"신한은행", "국민은행", "기업은행"};
    int[] balance = new int[SIZE];
    

    for (int i = 0; i < SIZE; i++) {

      System.out.print("번호 ?");
      no[i] = scanner.nextInt();

      System.out.print("이름 ? ");
      name[i] = scanner.next();

      System.out.print("비밀번호 ? ");
      password[i] = scanner.next();

      System.out.println("계좌번호를 입력하세요 ex) 00000000 ");
      accNum[i] = scanner.nextInt();

      System.out.println("은행을 선택하세요.");
      System.out.println("1. 신한은행 2.국민은행 3.하나은행");
      int num = scanner.nextInt();
      scanner.nextLine();
      bankName[i] = bankName[num -1];

      System.out.println("계좌개설을 축하드립니다.");
      balance[i] = 0;

      }

    System.out.println("--------------------------------");
    
    for (int i = 0; i < SIZE; i++) {
    System.out.printf("번호 %d\n", no[i]);
    System.out.printf("이름 : %s\n", name[i]);
    System.out.printf("계좌번호 : %d\n", accNum[i]);
    System.out.printf("은행명 : %s\n", bankName[i]);
    System.out.printf("계좌잔액 : $%d\n", balance[i]);
    }
    scanner.close();
  }
}
    