package bank_project.myapp.handler;

import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account/add")
public class AccountAddServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
 
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");

    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }

    Account account = new Account(); // 입력데이터 배열로 저장
    account.setOwner(loginUser);
    account.setAccNum(generateAccountNumber()); // Here we generate a new account number.
    account.setPassword(request.getParameter("password"));
    account.setBalance(0);

    try {
      InitServlet.accountDao.insert(account);
      InitServlet.sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list");

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "1;url=list");
      request.getRequestDispatcher("/error").forward(request, response);
    }

  }

  private String generateAccountNumber() {
    try {

      String maxAccNum = InitServlet.accountDao.findMaxAccNum();

      if (maxAccNum == null) {
        return "11100001";
      } else {
        int numberPart = Integer.parseInt(maxAccNum.substring(3));
        return "111" + String.format("%05d", numberPart + 1);
      }
    } catch (Exception e) {
      throw new RuntimeException("새 계좌를 발급할 수 없습니다.", e);
    }
  }
}
