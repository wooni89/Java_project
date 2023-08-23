package bank_project.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bank_project.myapp.dao.BoardDao;
import bank_project.myapp.vo.Board;
import bank_project.myapp.vo.Customer;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.Component;

@Component("/board/delete")
public class BoardDeleteListener implements ActionListener {

  BoardDao boardDao;
  SqlSessionFactory sqlSessionFactory;

  public BoardDeleteListener(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
    this.boardDao = boardDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Board b = new Board();
    b.setNo(prompt.inputInt("번호? "));
    b.setWriter((Customer) prompt.getAttribute("loginUser"));

    try {
      if (boardDao.delete(b) == 0) {
        prompt.println("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
      } else {
        prompt.println("삭제했습니다.");
      }
      sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}
