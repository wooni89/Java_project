package bank_project.myapp;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import bank_project.myapp.handler.BankingDepositListener;
import bank_project.myapp.handler.BankingWithrawListener;
import bank_project.myapp.handler.BoardAddListener;
import bank_project.myapp.handler.BoardDeleteListener;
import bank_project.myapp.handler.BoardDetailListener;
import bank_project.myapp.handler.BoardListListener;
import bank_project.myapp.handler.BoardUpdateListener;
import bank_project.myapp.handler.MemberAddListener;
import bank_project.myapp.handler.MemberDeleteListener;
import bank_project.myapp.handler.MemberDetailListener;
import bank_project.myapp.handler.MemberListListener;
import bank_project.myapp.handler.MemberUpdateListener;
import bank_project.myapp.vo.Board;
import bank_project.myapp.vo.CsvObject;
import bank_project.myapp.vo.Member;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.Menu;
import bank_project.util.MenuGroup;

public class App {

  ArrayList<Member> memberList = new ArrayList<>();
  ArrayList<Member> bankingList = new ArrayList<>();
  LinkedList<Board> boardList = new LinkedList<>();

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();

  MenuGroup mainMenu = new MenuGroup("메인");

  public App() {
    prepareMenu();
  }

  public static void main(String[] args) {

    new App().execute();

  }

  static void printTitle() {

    System.out.println("은행 업무 프로그램");
    System.out.println("----------------------------------");

  }

  public void execute() {
    printTitle();

    loadData();
    mainMenu.execute(prompt);
    saveData();

    prompt.close();
  }

  private void loadData() {
    loadCsv("member.csv", memberList, Member.class);
    loadCsv("board.csv", boardList, Board.class);
  }

  private void saveData() {
    saveCsv("member.csv", memberList);
    saveCsv("board.csv", boardList);
  }

  private void prepareMenu() {

    MenuGroup memberMenu = new MenuGroup("계좌업무");
    memberMenu.add(new Menu("계좌개설", new MemberAddListener(memberList)));
    memberMenu.add(new Menu("계좌리스트", new MemberListListener(memberList)));
    memberMenu.add(new Menu("계좌조회", new MemberDetailListener(memberList)));
    memberMenu.add(new Menu("계좌비밀번호 변경", new MemberUpdateListener(memberList)));
    memberMenu.add(new Menu("계좌삭제", new MemberDeleteListener(memberList)));
    mainMenu.add(memberMenu);

    MenuGroup bankingMenu = new MenuGroup("은행업무");
    bankingMenu.add(new Menu("입금", new BankingDepositListener(memberList)));
    bankingMenu.add(new Menu("출금", new BankingWithrawListener(memberList)));
    mainMenu.add(bankingMenu);

    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new Menu("고객불만작성", new BoardAddListener(boardList)));
    boardMenu.add(new Menu("게시글목록", new BoardListListener(boardList)));
    boardMenu.add(new Menu("게시글조회", new BoardDetailListener(boardList)));
    boardMenu.add(new Menu("게시글변경", new BoardUpdateListener(boardList)));
    boardMenu.add(new Menu("게시글삭제", new BoardDeleteListener(boardList)));
    mainMenu.add(boardMenu);

  }


  @SuppressWarnings("unchecked")
  private <T extends CsvObject> void loadCsv(String filename, List<T> list, Class<T> clazz) {
    try {
      Method factoryMethod = clazz.getDeclaredMethod("fromCsv", String.class);

      FileReader in0 = new FileReader(filename);
      BufferedReader in = new BufferedReader(in0);

      String line = null;

      while ((line = in.readLine()) != null) {
        list.add((T) factoryMethod.invoke(null, line));

      }

      in.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 읽는 중 오류 발생!");
    }
  }

  private void saveCsv(String filename, List<? extends CsvObject> list) {
    try {
      FileWriter out0 = new FileWriter(filename);
      BufferedWriter out1 = new BufferedWriter(out0);
      PrintWriter out = new PrintWriter(out1);

      for (CsvObject obj : list) {
        out.println(obj.toCsvString());

      }
      out.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 저장하는 중 오류 발생!");
    }
  }

}
