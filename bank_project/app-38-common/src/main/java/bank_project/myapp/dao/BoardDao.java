package bank_project.myapp.dao;

import java.util.List;
import bank_project.myapp.vo.Board;

public interface BoardDao {
  void insert(Board board);

  List<Board> list();

  Board findBy(int no);

  int update(Board board);

  int delete(Board board);
}
