package bank_project.myapp.handler;

import bank_project.myapp.vo.Board;
import bank_project.util.ActionListener;
import bank_project.util.List;

public abstract class AbstractBoardListener implements ActionListener {

  protected List<Board> list;

  public AbstractBoardListener(List<Board> list) {
    this.list = list;
  }

  protected Board findBy(int no) {
    for (int i = 0; i < this.list.size(); i++) {
      Board b = this.list.get(i);
      if (b.getNo() == no) {
        return b;
      }
    }
    return null;
  }
}
