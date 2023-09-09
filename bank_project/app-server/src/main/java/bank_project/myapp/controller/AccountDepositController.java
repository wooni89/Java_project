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

@WebServlet("/transaction/deposit")
public class AccountDepositController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
        Account account = accountDao.findAccount(request.getParameter("accNum"));
        request.setAttribute("account", account);
        request.setAttribute("viewUrl", "/WEB-INF/jsp/account/depositForm.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
        TransactionDao transactionDao = (TransactionDao) this.getServletContext().getAttribute("transactionDao");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");

        int amount = Integer.parseInt(request.getParameter("amount"));
        Account account = accountDao.findAccount(request.getParameter("accNum"));

        Account acc = new Account();
        acc.setAccNum(account.getAccNum());
        if (amount != 0) {
            acc.setBalance(account.getBalance() + amount);
        }

        Transaction transaction = new Transaction();
        transaction.setAcc_num(account);
        transaction.setTradeType("입금");
        transaction.setAmount(amount);
        transaction.setCustomer(request.getParameter("customer"));

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            accountDao.deposit(acc, amount);
            transactionDao.insert(transaction);
            sqlSessionFactory.openSession(false).commit();
            out.printf("<p>%s 계좌로 %d원 입금 완료!</p>", account.getAccNum(), amount);
            request.setAttribute("viewUrl", "redirect:/transaction/form?accNum=" + acc.getAccNum());
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("refresh", "2;url=/transaction/form?accNum=" + acc.getAccNum());
            request.setAttribute("exception", e);
        }
    }
}
