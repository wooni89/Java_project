package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.dao.TransactionDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Transaction;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/transaction/transfer")
public class AccountTransferController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
        Account account = accountDao.findAccount(request.getParameter("accNum"));
        request.setAttribute("account", account);
        request.setAttribute("viewUrl", "/WEB-INF/jsp/account/transferForm.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
        TransactionDao transactionDao = (TransactionDao) this.getServletContext().getAttribute("transactionDao");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");

        int amount = Integer.parseInt(request.getParameter("amount"));

        if (amount <= 0) {
            throw new ServletException("송금 금액은 0원 이상 입력해 주세요");
        }

        Account accountFrom = accountDao.findAccount(request.getParameter("fromAccNum"));
        Account fromAcc = new Account(); // 1번계좌
        fromAcc.setAccNum(accountFrom.getAccNum());
        if (amount != 0) {
            fromAcc.setBalance(accountFrom.getBalance() - amount);
        }

        if (accountFrom.getBalance() < amount) {
            throw new ServletException("계좌에 잔액이 부족합니다.");
        }

        Account accountTo = accountDao.findAccount(request.getParameter("toAccNum"));
        Account toAcc = new Account();
        toAcc.setAccNum(accountTo.getAccNum());
        if (amount != 0) {
            toAcc.setBalance(accountTo.getBalance() + amount);
        }

        if (accountTo == null) {
            throw new ServletException("계좌가 존재하지 않습니다");
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            accountDao.withdraw(accountFrom, amount);
            Transaction fromTx = new Transaction();
            fromTx.setAcc_num(accountFrom);
            fromTx.setTradeType("출금(계좌이체)");
            fromTx.setAmount(amount);
            fromTx.setCustomer(accountTo.getOwner().getName());
            transactionDao.insert(fromTx);

            accountDao.deposit(accountTo, amount);
            Transaction toTx = new Transaction();
            toTx.setAcc_num(accountTo);
            toTx.setTradeType("입금(계좌이체)");
            toTx.setAmount(amount);
            toTx.setCustomer(accountFrom.getOwner().getName());
            transactionDao.insert(toTx);

            sqlSessionFactory.openSession(false).commit();
            out.printf("<p>%s 계좌로 %d원 입금 완료!</p>", toAcc.getAccNum(), amount);
            request.setAttribute("viewUrl", "redirect:/app/transaction/form?accNum=" + fromAcc.getAccNum());
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("refresh", "2;url=/app/transaction/form?accNum=" + fromAcc.getAccNum());
            request.setAttribute("exception", e);
        }
    }
}

