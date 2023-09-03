package bank_project.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bank_project.myapp.vo.Account;

@WebServlet("/account/transfer")
public class BankingTransferServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
    Account transferAccount = new Account();
    transferAccount.setAccNum(request.getParameter("accNum"));
    transferAccount.setPassword(request.getParameter("password"));
    Account account = InitServlet.accountDao.findAccountPassword(transferAccount);
    int transferAmount = Integer.parseInt(request.getParameter("amount"));
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/account/list'>");
    out.println("<title>은행업무 - 송금</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>송금</h1>");
    
    if (account == null) {
      out.println("<p>회원이 없습니다.</p>");
      return;
    }

    if (transferAmount > account.getBalance()) {
      out.println("<p>계좌에 금액이 부족합니다.</p>.");
      return;
    } else if (transferAmount == 0) {
      out.println("<p>0원은 송금할 수 없습니다.</p>");
      return;
    }
    
    account.setBalance(account.getBalance() - transferAmount);
    
    Account receiveAccount = new Account();
    receiveAccount.setAccNum(request.getParameter("receiveAccNum"));
    Account depositAccount = InitServlet.accountDao.findAccount(receiveAccount);
    
    if (depositAccount == null) {
      out.println("<p>회원이 없습니다.</p>");
      return;
    }

    try {
      InitServlet.accountDao.withraw(account);
      InitServlet.accountDao.deposit(depositAccount);
      InitServlet.sqlSessionFactory.openSession(false).commit();

      out.println("-------------------");
      out.printf("$%,d 송금이 완료되었습니다.\n", transferAmount);
      out.println("-------------------");
      out.printf("출금자명 : %s\n", account.getName());
      out.printf("출금계좌번호 : %s\n", account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
      out.printf("거래 후 잔액 : $%,d\n", account.getBalance());
      out.println("-------------------");
      out.printf("입금자명 : %s\n", depositAccount.getName());
      out.printf("입금계좌번호 : %s\n", depositAccount.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
      out.printf("은행이름 : %s\n", depositAccount.getBankName());
      out.printf("입금액 : $%,d\n", transferAmount);
      out.println("-------------------");

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);
    }
  }
}
