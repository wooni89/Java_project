package bank_project.myapp.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import bank_project.myapp.vo.Customer;

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
  public Customer findBy(Customer customer) {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    return sqlSession.selectOne("bank_project.myapp.dao.CustomerDao.findBy", customer);
  }
  
  @Override
  public Customer update(Customer customer) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.update("bank_project.myapp.dao.CustomerDao.update", customer);
    return customer;
  }
  
  @Override
  public Customer delete(Customer customer) {
    SqlSession sqlSession = sqlSessionFactory.openSession(false);
    sqlSession.delete("bank_project.myapp.dao.CustomerDao.delete", customer);
    return customer;
  }
  
}
