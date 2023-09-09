package bank_project.myapp.dao;

import bank_project.myapp.vo.AttachedFile;
import bank_project.myapp.vo.Board;

import java.util.List;

public interface BoardDao {
  
  void insert(Board board);

  List<Board> findAll(int category);

  Board findBy(int category, int no);

  int update(Board board);
  
  int updateCount(Board board);
  
  int delete(Board board);
  
  int insertFiles(Board board);
  AttachedFile findFileBy(int no);
  int deleteFile(int fileNo);
  int deleteFiles(int no);
}
