package bank_project.myapp.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import bank_project.myapp.vo.Account;

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
  public Account findAccount(Account account) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bank_project.myapp.dao.AccountDao.findAccount", account);
  }

  @Override
  public Account findAccountPassword(Account account) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bank_project.myapp.dao.AccountDao.findAccountPassword", account);
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
  public Account deposit(Account account) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.update("bank_project.myapp.dao.AccountDao.deposit", account);
    return account;
}

  @Override
  public Account withraw(Account account) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.update("bank_project.myapp.dao.AccountDao.withraw", account);
    return account;
  }

  @Override
  public Account transfer(Account account) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.update("bank_project.myapp.dao.AccountDao.subtractBalance", account);
    sqlSession.update("bank_project.myapp.dao.AccountDao.addBalance", account);
    return account;
  }




}
