package bank_project.util;

import java.io.InputStream;
import java.util.Scanner;

public class Prompt {

  private Scanner scanner = new Scanner(System.in);

  public Prompt() {
    this.scanner = new Scanner(System.in);
  }

  public Prompt(InputStream in) {
    this.scanner = new Scanner(in);
  }

  public String inputString(String title, Object... args) {
    System.out.printf(title, args);
    return this.scanner.nextLine();
  }

  public int inputInt(String title, Object... args) {
    return Integer.parseInt(inputString(title, args));
  }

  public void close() {
    this.scanner.close();
  }

}
