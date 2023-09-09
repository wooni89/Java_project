package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account/update")
public class AccountUpdateController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");
    
    if (loginUser == null) {
      response.sendRedirect("/auth/form");
      return;
    }

    AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    
    Account account = new Account();
    account.setNo(Integer.parseInt(request.getParameter("no")));
    account.setOwner(loginUser);
    account.setPassword(request.getParameter("password"));

    try {
      if (accountDao.update(account) == 0) {
        throw new Exception("회원이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        request.setAttribute("viewUrl", "redirect:list");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list");
      request.setAttribute("exception", e);
    }
  }
}
