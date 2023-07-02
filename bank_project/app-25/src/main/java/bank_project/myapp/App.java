package bank_project.myapp;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    loadMember("member.data", memberList);
    loadBoard("board.data", boardList);
  }

  private void saveData() {
    saveMember("member.data", memberList);
    saveBoard("board.data", boardList);
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

  private void loadMember(String filename, List<Member> list) {
    try {
      FileInputStream in0 = new FileInputStream(filename);
      BufferedInputStream in1 = new BufferedInputStream(in0);
      ObjectInputStream in = new ObjectInputStream(in1);

      int size = in.readShort();

      for (int i = 0; i < size; i++) {
        list.add((Member) in.readObject());
      }

      if (list.size() > 0) {
        Member.userId = memberList.get(memberList.size() - 1).getNo() + 1;
      }
      in.close();

    } catch (Exception e) {
      System.out.println("회원 정보를 읽는 중 오류 발생!");
    }
  }

  private void loadBoard(String filename, List<Board> list) {
    try {
      FileInputStream in0 = new FileInputStream(filename);
      BufferedInputStream in1 = new BufferedInputStream(in0); // <== Decorator 역할을 수행!
      ObjectInputStream in = new ObjectInputStream(in1); // <== Decorator 역할을 수행!

      int size = in.readShort();

      for (int i = 0; i < size; i++) {
        list.add((Board) in.readObject());
      }

      if (list.size() > 0) {
        Board.boardNo = Math.max(Board.boardNo, list.get(list.size() - 1).getNo() + 1);
      }
      in.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 읽는 중 오류 발생!");
    }
  }

  private void saveMember(String filename, List<Member> list) {
    try {
      FileOutputStream out0 = new FileOutputStream(filename);
      BufferedOutputStream out1 = new BufferedOutputStream(out0); // <== Decorator(장식품) 역할 수행!
      ObjectOutputStream out = new ObjectOutputStream(out1); // <== Decorator(장식품) 역할 수행!

      // 저장할 데이터의 개수를 먼저 출력
      out.writeShort(memberList.size());

      for (Member member : list) {
        out.writeObject(member);
      }
      out.close();

    } catch (Exception e) {
      System.out.println("회원 정보를 저장하는 중 오류 발생!");
    }
  }

  private void saveBoard(String filename, List<Board> list) {
    try {
      FileOutputStream out0 = new FileOutputStream(filename);
      BufferedOutputStream out1 = new BufferedOutputStream(out0); // <== Decorator(장식품) 역할 수행!
      ObjectOutputStream out = new ObjectOutputStream(out1); // <== Decorator(장식품) 역할 수행!

      out.writeShort(list.size());

      for (Board board : list) {
        out.writeObject(board);
      }

      out.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 저장하는 중 오류 발생!");
    }
  }

}
