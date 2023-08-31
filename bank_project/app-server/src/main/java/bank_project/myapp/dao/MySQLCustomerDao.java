package bank_project.myapp.dao;

import bank_project.myapp.vo.Customer;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class MySQLCustomerDao implements CustomerDao {

  SqlSessionFactory sqlSessionFactory;
  
  public MySQLCustomerDao (SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }
  
  @Override
  public void insert(Customer customer) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.insert("bank_project.myapp.dao.CustomerDao.insert", customer);
  }
  
  @Override
  public List<Customer> findAll() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectList("bank_project.myapp.dao.CustomerDao.findAll");
  }
  
  @Override
  public Customer findBy(String customer) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bank_project.myapp.dao.CustomerDao.findBy", customer);
  }

  @Override
  public Customer findByEmailAndPassword(Customer customer) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bank_project.myapp.dao.CustomerDao.findByEmailAndPassword", customer);
  }

  @Override
  public Customer update(Customer customer) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.update("bank_project.myapp.dao.CustomerDao.update", customer);
    return customer;
  }
  
  @Override
  public int delete(String customer) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    return sqlSession.delete("bank_project.myapp.dao.CustomerDao.delete", customer);
  }
  
}
