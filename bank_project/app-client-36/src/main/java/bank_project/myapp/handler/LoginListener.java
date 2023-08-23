package bank_project.myapp.handler;

import bank_project.myapp.ClientApp;
import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.vo.Customer;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class LoginListener implements ActionListener {

  CustomerDao customerDao;
  
  public LoginListener(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }
  
  @Override
  public void service(BreadcrumbPrompt prompt) {
    while (true) {
      Customer cus = new Customer();
      cus.setEmail(prompt.inputString("이메일 : "));
      cus.setPassword(prompt.inputString("암호 : "));
      
      Customer loginUser = customerDao.findBy(cus);
      if(loginUser == null) {
        System.out.println("회원 정보가 없습니다.");
      } else {
        ClientApp.loginUser = loginUser;
        break;
      }
    }
  }
}
