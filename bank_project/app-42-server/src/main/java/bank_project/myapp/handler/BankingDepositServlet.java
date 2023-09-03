package bank_project.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bank_project.myapp.vo.Account;

@WebServlet("/banking/deposit")
public class BankingDepositServlet extends HttpServlet {
  
  private static final long serialVersionUID = 1L;


  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    Account tempAccount = new Account();
    tempAccount.setAccNum(request.getParameter("accNum"));
    tempAccount.setPassword(request.getParameter("password"));
    Account account = InitServlet.accountDao.findAccountPassword(tempAccount);
    int depositAmount = Integer.parseInt(request.getParameter("amount"));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='3;url=/account/list'>");
    out.println("<title>은행업무 - 입금</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>입금</h1>");
    
    if (account == null) {
      out.println("<p>회원이 없습니다.</p>");
      return;
    }
    
    if (depositAmount > 0) {
      account.setBalance(account.getBalance() + depositAmount);

        out.println("-------------------");
        out.printf("이름 : %s\n", account.getName());
        out.printf("은행명 : %s\n", account.getBankName());
        out.printf("계좌번호 : %s\n",
            account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
        out.printf("입금 후 잔액 : $%,d \n", account.getBalance());
        out.println("-------------------");
        
        try {
          InitServlet.accountDao.deposit(account);
          InitServlet.sqlSessionFactory.openSession(false).commit();
        } catch (Exception e) {
          InitServlet.sqlSessionFactory.openSession(false).rollback();
          throw new RuntimeException(e);
        }
    } else {
        out.println("<p>$0 이하의 금액은 입금이 불가합니다.</p>");
    }
    
    out.println("</body>");
    out.println("</html>");
  }
}
