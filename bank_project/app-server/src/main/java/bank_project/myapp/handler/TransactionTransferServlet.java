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

@WebServlet("/transaction/transfer")
public class TransactionTransferServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    int transferAmount = Integer.parseInt(request.getParameter("amount"));

    PrintWriter out = response.getWriter();

    if (transferAmount <= 0) {
     throw new ServletException ("송금 금액은 0원 이상 입력해 주세요");
    }

    Account accountFrom = InitServlet.accountDao.findAccount(request.getParameter("fromAccNum"));
    Account accountTo = InitServlet.accountDao.findAccount(request.getParameter("toAccNum"));

    if (accountFrom == null || accountTo == null) {
      out.println("<p>계좌가 존재하지 않습니다.</p>");
      return;
    }

    if (accountFrom.getBalance() < transferAmount) {
      out.println("<p>계좌에 잔액이 부족합니다.</p>");
      return;
    }

    // Create a new Transaction object and set the necessary fields
    Transaction transactionFrom = new Transaction();
    transactionFrom.setAcc_num(accountFrom);
    transactionFrom.setAmount(-transferAmount);

    Transaction transactionTo = new Transaction();
    transactionTo.setAcc_num(accountTo);
    transactionTo.setAmount(transferAmount);

    try {
      InitServlet.transactionDao.transfer(transactionFrom,transactionTo);
      InitServlet.sqlSessionFactory.openSession(false).commit();

    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      throw new RuntimeException(e);

    }
  }
}

