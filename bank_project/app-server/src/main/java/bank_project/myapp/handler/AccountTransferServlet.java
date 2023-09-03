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

@WebServlet("/account/transfer")
public class AccountTransferServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {

    int amount = Integer.parseInt(request.getParameter("amount"));

    if (amount <= 0) {
     throw new ServletException ("송금 금액은 0원 이상 입력해 주세요");
    }

    Account accountFrom = InitServlet.accountDao.findAccount(request.getParameter("fromAccNum"));
    Account fromAcc = new Account(); // 1번계좌
      fromAcc.setAccNum(accountFrom.getAccNum());
    if (amount != 0) {
      fromAcc.setBalance(accountFrom.getBalance() - amount);
    }

    if (accountFrom.getBalance() < amount) {
      throw new ServletException ("계좌에 잔액이 부족합니다.");
    }

    Account accountTo = InitServlet.accountDao.findAccount(request.getParameter("toAccNum"));
    Account toAcc = new Account();
    toAcc.setAccNum(accountTo.getAccNum());
    if (amount != 0) {
      toAcc.setBalance(accountTo.getBalance() + amount);
    }

    if (accountTo == null) {
      throw new ServletException ("계좌가 존재하지 않습니다");
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/account/list'>");
    out.println("<title>은행업무 - 계좌이체</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>계좌이체</h1>");

    try {
    InitServlet.accountDao.withdraw(accountFrom, amount);
    Transaction fromTx = new Transaction();
    fromTx.setAcc_num(accountFrom);
    fromTx.setTradeType("출금(계좌이체)");
    fromTx.setAmount(amount);
    fromTx.setCustomer(accountTo.getOwner().getName());
    InitServlet.transactionDao.insert(fromTx);

    InitServlet.accountDao.deposit(accountTo, amount);
    Transaction toTx = new Transaction();
    toTx.setAcc_num(accountTo);
    toTx.setTradeType("입금(계좌이체)");
    toTx.setAmount(amount);
    toTx.setCustomer(accountFrom.getOwner().getName());
    InitServlet.transactionDao.insert(toTx);

    InitServlet.sqlSessionFactory.openSession(false).commit();

      out.printf("<p>%s 계좌로 %d원 입금 완료!</p>", toAcc.getAccNum(), amount);
    } catch (Exception e) {
      InitServlet.sqlSessionFactory.openSession(false).rollback();
      throw new ServletException(e);

    }
  }
}

