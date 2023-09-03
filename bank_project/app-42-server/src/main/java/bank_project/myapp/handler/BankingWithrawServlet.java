package bank_project.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bank_project.myapp.vo.Account;

@WebServlet("/account/withraw")
public class BankingWithrawServlet extends HttpServlet {
  
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Account tempAccount = new Account();
    tempAccount.setAccNum(request.getParameter("accNum"));
    tempAccount.setPassword(request.getParameter("password"));
    Account account = InitServlet.accountDao.findAccountPassword(tempAccount);
    int withrawAmount = Integer.parseInt(request.getParameter("amount"));
      
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
    
    if (account == null) {
      out.println("<p>회원이 없습니다.</p>");
      return;
    }
     

    if (account.getBalance() < withrawAmount) {
        out.println("<p>잔고가 부족하여 출금이 불가합니다.</p>");
    } else if (withrawAmount == 0) {
        out.println("<p>$0는 출금할 수 없습니다.</p>");
    } else {
      account.setBalance(account.getBalance() - withrawAmount);
  
      out.println("-------------------");
      out.printf("이름 : %s\n", account.getName());
      out.printf("은행명 : %s\n", account.getBankName());
      out.printf("계좌번호 : %s\n",
          account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
      out.printf("출금 후 잔액 : $%,d \n", account.getBalance());
      out.println("-------------------");
      
      try {
        InitServlet.accountDao.withraw(account);
        InitServlet.sqlSessionFactory.openSession(false).commit();
        out.println("<p>출금 완료!</p>");
      } catch (Exception e) {
        InitServlet.sqlSessionFactory.openSession(false).rollback();
        out.println("<p>출금실패!</p>");
        e.printStackTrace();
      }
    }
    out.println("</body>");
    out.println("</html>");
    }

  }

