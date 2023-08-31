package bank_project.myapp.dao;

import bank_project.myapp.vo.Account;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class MySQLAccountDao implements AccountDao {

  SqlSessionFactory sqlSessionFactory;

  public MySQLAccountDao (SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void insert(Account account) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.insert("bank_project.myapp.dao.AccountDao.insert", account);
  }

  @Override
  public List<Account> findAll() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectList("bank_project.myapp.dao.AccountDao.findAll");
  }

  @Override
  public Account findAccount(String account) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bank_project.myapp.dao.AccountDao.findAccount", account);
  }

  @Override
  public Account findByAccountAndOwner(int customer) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bank_project.myapp.dao.AccountDao.findByAccountAndOwner", customer);
  }

  @Override
  public boolean findAccountAndPassword(Account account) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    Integer count = sqlSession.selectOne("bank_project.myapp.dao.AccountDao.findAccountAndPassword", account);
    return count != null && count > 0; // 계좌와 비밀번호 일치 여부 확인 후 true 또는 false 반환
  }

  @Override
  public Account update(Account account) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.update("bank_project.myapp.dao.AccountDao.update", account);
    return account;
  }

  @Override
  public Account delete(Account account) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.delete("bank_project.myapp.dao.AccountDao.delete", account);
    return account;
  }

  @Override
  public String findMaxAccNum() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bank_project.myapp.dao.AccountDao.findMaxAccNum");
  }

  @Override
  public Account deposit(Account account) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.update("bank_project.myapp.dao.AccountDao.deposit", account);
    return account;
  }
}
