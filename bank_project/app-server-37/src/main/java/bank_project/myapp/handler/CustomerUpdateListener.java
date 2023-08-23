package bank_project.myapp.handler;

import java.io.IOException;
import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.vo.Customer;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class CustomerUpdateListener implements ActionListener {

  CustomerDao customerDao;

  public CustomerUpdateListener(CustomerDao customerDao) {
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
      System.out.println("해당 회원이 없습니다!");
      return;
    }

    customer.setName(prompt.inputString("이름(%s)? ", customer.getName()));
    customer.setPhoneNumber(prompt.inputString("전화번호(%s)? ", customer.getPhoneNumber()));
    customer.setPassword(prompt.inputString("비밀번호? "));
    System.out.println("변경이 완료되었습니다.");

    customerDao.update(customer);
  }
}
