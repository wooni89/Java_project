package bank_project.myapp.handler;

import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/account/list'>");
    out.println("<title>계좌업무</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>계좌 개설</h1>");

    try {
      InitServlet.accountDao.insert(account);
      InitServlet.sqlSessionFactory.openSession(false).commit();
      out.println("<p>축하합니다 계좌가 개설되었습니다!</p>");

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>계좌 개설에 실패하였습니다!</p>");
      throw new RuntimeException(e);
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
