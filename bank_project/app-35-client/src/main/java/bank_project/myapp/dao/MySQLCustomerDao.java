package bank_project.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bank_project.myapp.vo.Customer;

public class MySQLCustomerDao implements CustomerDao {

  Connection con;
  
  public MySQLCustomerDao(Connection con) {
    this.con = con;
  }
  
  @Override
  public void insert(Customer customer) {
    try (PreparedStatement stmt = con.prepareStatement(
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
    try (PreparedStatement stmt = con.prepareStatement(
            "select " + "customer_no, name, email, phone_number, created_date"
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
  public Customer findBy(Customer customer) {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(String.format(
            "select " + "customer_no, name, email, password, phone_number"
                + " from myapp_customer" + " where email='%s'" + " and password='%s'",
                customer.getEmail(), customer.getPassword()))) {

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

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public Customer update(Customer customer) {
    try (Statement stmt = con.createStatement()) {
      stmt.executeUpdate(String.format(
          "update myapp_customer set"
              + " name='%s',"
              + " phone_number='%s',"
              + " password='%s'"
              + " where email='%s'",
              customer.getName(),
              customer.getPhoneNumber(),
              customer.getPassword(),
              customer.getEmail()));

      return customer;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public Customer delete(Customer customer) {
    try (Statement stmt = con.createStatement()) {
      stmt.executeUpdate(String.format(
          "delete from myapp_customer where email='%s'",
          customer.getEmail()));

      return customer;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
}
