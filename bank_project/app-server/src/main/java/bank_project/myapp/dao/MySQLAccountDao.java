  package bank_project.myapp.dao;

  import bank_project.myapp.vo.Account;
  import org.apache.ibatis.session.SqlSession;
  import org.apache.ibatis.session.SqlSessionFactory;

  import java.util.HashMap;
  import java.util.List;
  import java.util.Map;

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
    public int update(Account account) {
      SqlSession sqlSession = sqlSessionFactory.openSession(false);
      return sqlSession.update("bank_project.myapp.dao.AccountDao.update", account);
    }

    @Override
    public int delete(Account account) {
      SqlSession sqlSession = sqlSessionFactory.openSession(false);
      return sqlSession.delete("bank_project.myapp.dao.AccountDao.delete", account);
    }

    @Override
    public String findMaxAccNum() {
      SqlSession sqlSession = sqlSessionFactory.openSession();
      return sqlSession.selectOne("bank_project.myapp.dao.AccountDao.findMaxAccNum");
    }

    @Override
    public int deposit(Account account, int amount) {
      SqlSession sqlSession = sqlSessionFactory.openSession(false);
      Map <String, Object> params = new HashMap<>();
      params.put("accNum" ,account);
      params.put("amount", amount);
      return sqlSession.update("bank_project.myapp.dao.AccountDao.deposit", params);
    }

    @Override
    public int withdraw(Account account, int amount) {
      SqlSession sqlSession = sqlSessionFactory.openSession(false);
      Map <String, Object> params = new HashMap<>();
      params.put("accNum" ,account);
      params.put("amount", amount);
      return sqlSession.update("bank_project.myapp.dao.AccountDao.withdraw", params);
    }

  }
