package bank_project.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.vo.Customer;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.Component;

@Component("/customer/delete")
public class CustomerDeleteListener implements ActionListener {

  CustomerDao customerDao;
  SqlSessionFactory sqlSessionFactory;

  public CustomerDeleteListener(CustomerDao customerDao, SqlSessionFactory sqlSessionFactory) {
    this.customerDao = customerDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {

    String email = prompt.inputString("이메일 : ");
    String password = prompt.inputString("비밀번호 : ");
    Customer tempCustom = new Customer();
    tempCustom.setEmail(email);
    tempCustom.setPassword(password);

    Customer customer = customerDao.findBy(tempCustom);

    try {
      if (customer != null) {
        Customer deletedCustomer = customerDao.delete(customer);

        if (deletedCustomer != null) {
          prompt.println("정상적으로 삭제되었습니다. 이용해주셔서 감사합니다.");
          sqlSessionFactory.openSession(false).commit();
        } else {
          prompt.println("삭제에 실패했습니다. 다시 시도해주세요.");
          return;
        }
      } else {
        prompt.println("입력하신 정보와 일치하는 회원이 없습니다.");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}
