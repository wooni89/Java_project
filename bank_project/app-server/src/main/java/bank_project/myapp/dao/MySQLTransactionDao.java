package bank_project.myapp.dao;

import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Transaction;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLTransactionDao implements TransactionDao {
    SqlSessionFactory sqlSessionFactory;

    public MySQLTransactionDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    // MySQLTransactionDao.java
    @Override
    public void deposit(Transaction transaction) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        sqlSession.insert("bank_project.myapp.dao.TransactionDao.deposit", transaction);
    }

    @Override
    public void withdraw(Transaction transaction) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        sqlSession.selectOne("bank_project.myapp.dao.AccountDao.findAccount", transaction.getAcc_num());
    }


    @Override
    public void transfer(Transaction from, Transaction to) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("from", from);
        paramMap.put("to", to);
        sqlSession.insert("bank_project.myapp.dao.TransactionDao.transfer", paramMap);
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
