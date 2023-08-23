package bank_project.myapp.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bank_project.myapp.vo.Customer;

@WebServlet("/customer/delete")
public class CustomerDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Customer tempCustom = new Customer();
    tempCustom.setEmail(request.getParameter("email"));
    tempCustom.setPassword(request.getParameter("password"));
    Customer customer = InitServlet.customerDao.findBy(tempCustom);

    try {
      if (customer != null) {
        Customer deletedCustomer = InitServlet.customerDao.delete(customer);

        if (deletedCustomer != null) {
          InitServlet.sqlSessionFactory.openSession(false).commit();
        } else {
          throw new Exception("삭제에 실패했습니다. 다시 시도해주세요.");
        }
      } else {
        throw new Exception("입력하신 정보와 일치하는 회원이 없습니다.");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}
