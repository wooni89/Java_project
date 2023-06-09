package bank_project.util;

import java.util.Scanner;

public class Prompt {

  static Scanner scanner = new Scanner(System.in);

  public static String inputStirng(String title) {
    System.out.print(title);
    return scanner.nextLine();
  }

  public static int inputIntStirng(String titleInt) {
    System.out.println(titleInt);
    return scanner.nextInt();
  }

  public static void close() {
    scanner.close();
  }

}
