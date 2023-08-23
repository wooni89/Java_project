package bank_project.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.vo.Customer;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class CustomerAddListener implements ActionListener {

  CustomerDao customerDao;
  SqlSessionFactory sqlSessionFactory;

  public CustomerAddListener(CustomerDao customerDao, SqlSessionFactory sqlSessionFactory) {
    this.customerDao = customerDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    Customer customer = new Customer();
    customer.setName(prompt.inputString("이름\n -> "));
    customer.setEmail(prompt.inputString("이메일\n -> "));
    customer.setPassword(prompt.inputString("비밀번호\n -> "));
    customer.setPhoneNumber(prompt.inputString("연락처\n -> "));

    try {
      customerDao.insert(customer);
      sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}
