package bank_project.myapp.handler;

import java.io.IOException;
import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.vo.Customer;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class CustomerDetailListener implements ActionListener {

  CustomerDao customerDao;

  public CustomerDetailListener(CustomerDao customerDao) {
    this.customerDao = customerDao;

  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    String email = prompt.inputString("이메일 : ");
    String password = prompt.inputString("비밀번호 : ");
    Customer tempCustom = new Customer();
    tempCustom.setEmail(email);
    tempCustom.setPassword(password);

    Customer customer = customerDao.findBy(tempCustom);
    
    if (customer == null) {
      prompt.println("해당 번호의 게시글이 없습니다!");
      return;
    }

    prompt.printf("이름: %s\n", customer.getName());
    prompt.printf("이메일: %s\n", customer.getEmail());
    prompt.printf("연락처: %s\n", customer.getPhoneNumber());
    prompt.printf("가입일: %tY-%1$tm-%1$td\n", customer.getCreatedDate());

    customerDao.update(customer);
  }
}
