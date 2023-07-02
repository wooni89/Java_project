package bank_project.myapp;


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
import bank_project.util.ArrayList;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.LinkedList;
import bank_project.util.Menu;
import bank_project.util.MenuGroup;

public class App {

  public static void main(String[] args) {

    ArrayList memberList = new ArrayList();
    ArrayList bankingList = new ArrayList();
    LinkedList boardList = new LinkedList();

    BreadcrumbPrompt prompt = new BreadcrumbPrompt();

    MenuGroup mainMenu = new MenuGroup("메인");

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


    printTitle();

    mainMenu.execute(prompt);;


    prompt.close();

  }

  static void printTitle() {

    System.out.println("은행 업무 프로그램");
    System.out.println("----------------------------------");

  }
}
