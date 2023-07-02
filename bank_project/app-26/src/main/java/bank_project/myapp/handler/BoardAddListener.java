package bank_project.myapp.handler;

import java.util.List;
import bank_project.myapp.vo.Board;
import bank_project.util.BreadcrumbPrompt;

public class BoardAddListener extends AbstractBoardListener {

  public BoardAddListener(List<Board> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    Board board = new Board();
    board.setTitle(prompt.inputString("게시물 제목 : "));
    board.setContent(prompt.inputString("게시물 내용 : "));
    board.setWriter(prompt.inputString("작성자 : "));
    board.setPassword(prompt.inputString("암호 : "));

    this.list.add(board);

  }

}
