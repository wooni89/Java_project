package bank_project.myapp.handler;

import java.text.SimpleDateFormat;
import java.util.List;
import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.vo.Customer;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class CustomerListListener implements ActionListener {

  CustomerDao customerDao;
  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  public CustomerListListener(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    System.out.println("---------------------------------------");
    System.out.println("번호, 아이디, 이메일, 연락처, 등록일");
    System.out.println("---------------------------------------");

    List<Customer> list = customerDao.list();

    for (Customer cstm : list) {
      String formattedPhoneNumber = 
          cstm.getPhoneNumber().replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
      System.out.printf("%d, %s, %s, %s, %s\n",
          cstm.getNo(),
          cstm.getName(),
          cstm.getEmail(),
          formattedPhoneNumber,
          dateFormatter.format(cstm.getCreatedDate()));
    }
  }

}
