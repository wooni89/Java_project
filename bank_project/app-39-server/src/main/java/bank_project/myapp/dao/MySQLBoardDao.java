package bank_project.myapp.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import bank_project.myapp.vo.Board;

public class MySQLBoardDao implements BoardDao {
  
  SqlSessionFactory sqlSessionFactory;
  
  public MySQLBoardDao (SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Board board) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.insert("bank_project.myapp.dao.BoardDao.insert", board);
    
  }

  @Override
  public List<Board> findAll() {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.selectList("bank_project.myapp.dao.BoardDao.findAll");
  }

  @Override
  public Board findBy(int no) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bank_project.myapp.dao.BoardDao.findBy", no);
  }

  @Override
  public int update(Board board) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.update("bank_project.myapp.dao.BoardDao.update", board);
  }

  @Override
  public int delete(Board board) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("bank_project.myapp.dao.BoardDao.delete", board);
  }

}
