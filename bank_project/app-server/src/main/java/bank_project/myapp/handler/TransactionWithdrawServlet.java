package bank_project.myapp.handler;

import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/transaction/withdraw")
public class TransactionWithdrawServlet extends HttpServlet {
  
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Account account = InitServlet.accountDao.findAccount(request.getParameter("accNum"));
    int withdrawAmount = Integer.parseInt(request.getParameter("amount"));

    Transaction transaction = new Transaction();
    transaction.setAcc_num(account);
    transaction.setAmount(withdrawAmount);
      

    if (account == null) {
      throw new ServletException("해당 계좌가 없습니다");
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/account/list'>");
    out.println("<title>은행업무 - 출금</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>출금</h1>");

      
    try {
      InitServlet.transactionDao.withdraw(transaction);
      InitServlet.sqlSessionFactory.openSession(false).commit();
      out.println("<p>출금 완료!</p>");
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      out.println("<p>출금실패!</p>");
      e.printStackTrace();
    }

    out.println("</body>");
    out.println("</html>");
    }
  }



