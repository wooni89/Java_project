package bank_project.myapp;


import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    loadMember();
    loadBoard("board.data", boardList);
  }

  private void saveData() {
    saveMember();
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

  private void loadMember() {
    try {
      FileInputStream in = new FileInputStream("member.data");
      int size = in.read() << 8;
      size |= in.read();

      byte[] buf = new byte[1000];

      for (int i = 0; i < size; i++) {
        Member member = new Member();
        member.setNo(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        int length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        member.setName(new String(buf, 0, length, "UTF-8"));

        length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        member.setAccNum(new String(buf, 0, length, "UTF-8"));

        length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        member.setPassword(new String(buf, 0, length, "UTF-8"));

        length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        member.setBankName(new String(buf, 0, length, "UTF-8"));

        member.setBalance(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        memberList.add(member);
      }

      // 데이터를 로딩한 이후에 추가할 회원의 번호를 설정한다.
      Member.userId = memberList.get(memberList.size() - 1).getNo() + 1;

      in.close();

    } catch (Exception e) {
      System.out.println("회원 정보를 읽는 중 오류 발생!");
    }
  }

  private void loadBoard(String filename, List<Board> list) {
    try {
      FileInputStream in = new FileInputStream(filename);
      int size = in.read() << 8;
      size |= in.read();

      byte[] buf = new byte[1000];

      for (int i = 0; i < size; i++) {
        Board board = new Board();
        board.setNo(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        int length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        board.setTitle(new String(buf, 0, length, "UTF-8"));

        length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        board.setContent(new String(buf, 0, length, "UTF-8"));

        length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        board.setWriter(new String(buf, 0, length, "UTF-8"));

        length = in.read() << 8 | in.read();
        in.read(buf, 0, length);
        board.setPassword(new String(buf, 0, length, "UTF-8"));

        board.setViewCount(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        board.setCreatedDate((long) in.read() << 56 | (long) in.read() << 48
            | (long) in.read() << 40 | (long) in.read() << 32 | (long) in.read() << 24
            | (long) in.read() << 16 | (long) in.read() << 8 | in.read());

        list.add(board);
      }

      Board.boardNo = Math.max(Board.boardNo, list.get(list.size() - 1).getNo() + 1);

      in.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 읽는 중 오류 발생!");
    }
  }

  private void saveMember() {
    try {
      FileOutputStream out = new FileOutputStream("member.data");

      // 저장할 데이터의 개수를 먼저 출력한다.
      int size = memberList.size();
      out.write(size >> 8);
      out.write(size);

      for (Member member : memberList) {
        int no = member.getNo();
        out.write(no >> 24);
        out.write(no >> 16);
        out.write(no >> 8);
        out.write(no);

        byte[] bytes = member.getName().getBytes("UTF-8");
        // 출력할 바이트의 개수를 2바이트로 표시한다.
        out.write(bytes.length >> 8);
        out.write(bytes.length);

        // 문자열의 바이트를 출력한다.
        out.write(bytes);

        bytes = member.getAccNum().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = member.getPassword().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = member.getBankName().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        int balance = member.getBalance();
        out.write(balance >> 24);
        out.write(balance >> 16);
        out.write(balance >> 8);
        out.write(balance);

      }
      out.close();

    } catch (Exception e) {
      System.out.println("회원 정보를 저장하는 중 오류 발생!");
    }
  }

  private void saveBoard(String filename, List<Board> list) {
    try {
      FileOutputStream out = new FileOutputStream(filename);

      // 저장할 데이터의 개수를 먼저 출력한다.
      int size = list.size();
      out.write(size >> 8);
      out.write(size);

      for (Board board : list) {
        int no = board.getNo();
        out.write(no >> 24);
        out.write(no >> 16);
        out.write(no >> 8);
        out.write(no);

        byte[] bytes = board.getTitle().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);


        bytes = board.getContent().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = board.getWriter().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        bytes = board.getPassword().getBytes("UTF-8");
        out.write(bytes.length >> 8);
        out.write(bytes.length);
        out.write(bytes);

        int viewCount = board.getViewCount();
        out.write(viewCount >> 24);
        out.write(viewCount >> 16);
        out.write(viewCount >> 8);
        out.write(viewCount);

        long createdDate = board.getCreatedDate();
        out.write((int) (createdDate >> 56));
        out.write((int) (createdDate >> 48));
        out.write((int) (createdDate >> 40));
        out.write((int) (createdDate >> 32));
        out.write((int) (createdDate >> 24));
        out.write((int) (createdDate >> 16));
        out.write((int) (createdDate >> 8));
        out.write((int) createdDate);
      }
      out.close();

    } catch (Exception e) {
      System.out.println(filename + " 파일을 저장하는 중 오류 발생!");
    }
  }

}
