package bank_project.myapp.handler;

import bank_project.myapp.vo.Board;
import bank_project.util.List;
import bank_project.util.Prompt;

public class BoardHandler implements Handler {

  private List list;
  private Prompt prompt;
  private String title;


  public BoardHandler(Prompt prompt, String title, List list) {
    this.prompt = prompt;
    this.title = title;
    this.list = list;
  }

  public void execute() {
    printMenu();

    while (true) {
      String menuNo = prompt.inputString(" %s > ", this.title);
      if (menuNo.equals("0")) {
        return;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        this.inputBoard();
      } else if (menuNo.equals("2")) {
        this.printBoards();
      } else if (menuNo.equals("3")) {
        this.viewBoard();
      } else if (menuNo.equals("4")) {
        this.updateBoard();
      } else if (menuNo.equals("5")) {
        this.deleteBoard();
      } else {
        System.out.println("번호를 다시 입력해 주세요");
      }
    }
  }

  private static void printMenu() {
    System.out.println("[1] 고객불만작성");
    System.out.println("[2] 게시글목록");
    System.out.println("[3] 게시글조회");
    System.out.println("[4] 게시글변경");
    System.out.println("[5] 게시글삭제");
    System.out.println("[0] 메인");
  }

  public void inputBoard() {

    Board board = new Board();
    board.setTitle(this.prompt.inputString("게시물 제목 : "));
    board.setContent(this.prompt.inputString("게시물 내용 : "));
    board.setWriter(this.prompt.inputString("작성자 : "));
    board.setPassword(this.prompt.inputString("암호 : "));

    this.list.add(board);

  }

  public void printBoards() { // 게시판 리스트

    System.out.println("------------------------------");
    System.out.println("번호, 제목, 작성자, 조회수, 작성일 ");
    System.out.println("------------------------------");

    for (int i = 0; i < this.list.size(); i++) {
      Board board = (Board) this.list.get(i);
      System.out.printf("%d, %s , %s ,%d, %5$tY-%5$tm-%5$td \n", board.getNo(), board.getTitle(),
          board.getWriter(), board.getViewCount(), board.getCreatedDate());
    }
  }

  public void viewBoard() {

    int boardNo = this.prompt.inputInt("번호 ? ");
    Board board = this.findBy(boardNo);

    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    System.out.printf("제목 : %s\n", board.getTitle());
    System.out.printf("내용 : %s\n", board.getContent());
    System.out.printf("작성자 : %s\n", board.getWriter());
    System.out.printf("작성일 : %1$tY-%1$tm-%1$td \n", board.getCreatedDate());
    System.out.printf("조회수 : %d\n", board.getViewCount());
    board.setViewCount(board.getViewCount() + 1);
  }

  public void updateBoard() {

    int boardNo = this.prompt.inputInt("번호 ? ");
    Board board = this.findBy(boardNo);

    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    if (!this.prompt.inputString("암호 ? ").equals(board.getPassword())) {
      System.out.println("암호가 일치하지 않습니다.");
      return;
    }
    board.setTitle(this.prompt.inputString("제목(%s) ?", board.getTitle()));
    board.setContent(this.prompt.inputString("내용(%s) ?", board.getContent()));
    return;

  }



  public void deleteBoard() {

    if (!this.list.remove(new Board(this.prompt.inputInt("번호 ? ")))) {
      System.out.println("해당 번호에 게시글이 없습니다.");
    }

  }

  private Board findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Board b = (Board) this.list.get(i);
      if (b.getNo() == no) {
        return b;
      }
    }
    return null;
  }



}
