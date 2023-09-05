package bank_project.myapp.handler;

import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account/update")
public class AccountUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");
    
    if (loginUser == null) {
      response.sendRedirect("/auth/form.html");
      return;
    }
    
    Account account = new Account();
    account.setNo(Integer.parseInt(request.getParameter("no")));
    account.setOwner(loginUser);
    account.setPassword(request.getParameter("password"));

    try {
      if (InitServlet.accountDao.update(account) == 0) {
        throw new Exception("회원이 없습니다.");
      } else {
        InitServlet.sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list");
      }
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "1;url=list");
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
