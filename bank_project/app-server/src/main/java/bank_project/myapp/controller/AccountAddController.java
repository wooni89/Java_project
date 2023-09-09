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

@WebServlet("/account/add")
public class AccountAddController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setAttribute("viewUrl", "/WEB-INF/jsp/account/form.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");

    if (loginUser == null) {
      request.setAttribute("viewUrl", "redirect:../auth/login");
      return;
    }

    Account account = new Account(); // 입력데이터 배열로 저장
    account.setOwner(loginUser);
    account.setAccNum(generateAccountNumber()); // Here we generate a new account number.
    account.setPassword(request.getParameter("password"));
    account.setBalance(0);

    AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");

    try {
      accountDao.insert(account);
      sqlSessionFactory.openSession(false).commit();
      request.setAttribute("viewUrl", "redirect:list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list");
      request.setAttribute("exception", e);
    }
  }

  private String generateAccountNumber() {
    try {
      AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
      String maxAccNum = accountDao.findMaxAccNum();

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
