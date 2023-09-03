package bank_project.myapp.dao;

import bank_project.myapp.vo.Customer;

import java.util.List;

public interface CustomerDao {

  void insert(Customer customer);
  
  List<Customer> findAll();
  
  Customer findBy(String customer);

  Customer findByEmailAndPassword(Customer customer);
  
  int update(Customer customer);
  
  int delete(String customer);
  
}

