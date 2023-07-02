package bank_project.myapp.handler;

import java.util.List;
import bank_project.myapp.vo.Board;
import bank_project.util.BreadcrumbPrompt;

public class BoardDeleteListener extends AbstractBoardListener {

  public BoardDeleteListener(List<Board> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    if (!this.list.remove(new Board(prompt.inputInt("번호 ? ")))) {
      System.out.println("해당 번호에 게시글이 없습니다.");
    }

  }

}
