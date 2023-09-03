package bank_project.myapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bank_project.myapp.vo.Customer;
import bank_project.util.DataSource;

public class MySQLCustomerDao implements CustomerDao {

  DataSource ds;
  
  public MySQLCustomerDao(DataSource ds) {
    this.ds = ds;
  }
  
  @Override
  public void insert(Customer customer) {
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
          "insert into myapp_customer(name, email, password, phone_number)"
              + " values(?,?,sha1(?),?)")) {
          stmt.setString(1, customer.getName());
          stmt.setString(2, customer.getEmail());
          stmt.setString(3, customer.getPassword());
          stmt.setString(4, customer.getPhoneNumber());
          
          stmt.executeUpdate();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public List<Customer> list() {
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
            "select customer_no, name, email, phone_number, created_date"
                + " from myapp_customer" + " order by customer_no");
        ResultSet rs = stmt.executeQuery()) {

      List<Customer> list = new ArrayList<>();

      while (rs.next()) {
        Customer customer = new Customer();
        customer.setNo(rs.getInt("customer_no"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhoneNumber(rs.getString("phone_number"));
        customer.setCreatedDate(rs.getTimestamp("created_date"));

        list.add(customer);
      }

      return list;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public Customer findBy(Customer param) {
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
            "select customer_no, name, email, password, phone_number"
                + " from myapp_customer where email=? and password=sha1(?)")) {
      
      stmt.setString(1, param.getEmail());
      stmt.setString(2, param.getPassword());

      try (ResultSet rs = stmt.executeQuery()) {
      if (rs.next()) {
        Customer cstm = new Customer();
        cstm.setNo(rs.getInt("customer_no"));
        cstm.setName(rs.getString("name"));
        cstm.setEmail(rs.getString("email"));
        cstm.setPassword(rs.getString("password"));
        cstm.setPhoneNumber(rs.getString("phone_number"));

        return cstm;
      }
      return null;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public Customer update(Customer customer) {
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
          "update myapp_customer set"
              + " name=?,"
              + " phone_number=?,"
              + " password=sha1(?)"
              + " where email=?")) {
      
          stmt.setString(1, customer.getName());
          stmt.setString(2, customer.getPhoneNumber());
          stmt.setString(3, customer.getPassword());
          stmt.setString(4, customer.getEmail());

          stmt.executeUpdate();
          
      return customer;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public Customer delete(Customer customer) {
    try (PreparedStatement stmt = ds.getConnection(false).prepareStatement(
          "delete from myapp_customer where email=?")) {
          
      stmt.setString(1, customer.getEmail());

      stmt.executeUpdate();
      
      return customer;
      
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
}
