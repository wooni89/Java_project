package bank_project.myapp.dao;

import java.util.List;
import bank_project.myapp.vo.Customer;

public interface CustomerDao {

  void insert(Customer customer);
  
  List<Customer> list();
  
  Customer findBy(Customer customer);
  
  Customer update(Customer customer);
  
  Customer delete(Customer customer);
  
}

