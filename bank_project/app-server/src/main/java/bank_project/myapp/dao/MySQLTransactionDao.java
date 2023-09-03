package bank_project.myapp.dao;

import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Transaction;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class MySQLTransactionDao implements TransactionDao {
    SqlSessionFactory sqlSessionFactory;

    public MySQLTransactionDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    // MySQLTransactionDao.java
    @Override
    public void insert(Transaction transaction) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        sqlSession.insert("bank_project.myapp.dao.TransactionDao.insert", transaction);
    }

    @Override
    public List<Transaction> findAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.selectList("bank_project.myapp.dao.TransactionDao.findAll");
    }

    @Override
    public List<Transaction> findByAccountNumber(Account account) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.selectList("bank_project.myapp.dao.TransactionDao.findByAccountNumber", account);
    }
}
