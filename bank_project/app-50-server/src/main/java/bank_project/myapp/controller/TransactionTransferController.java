package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.dao.TransactionDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Transaction;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TransactionTransferController implements PageController {

   AccountDao accountDao;
   TransactionDao transactionDao;
   SqlSessionFactory sqlSessionFactory;

    public TransactionTransferController(AccountDao accountDao, TransactionDao transactionDao, SqlSessionFactory sqlSessionFactory) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (request.getMethod().equals("GET")) {
        Account account = accountDao.findAccount(request.getParameter("accNum"));
        request.setAttribute("account", account);
        return "/WEB-INF/jsp/transaction/transferForm.jsp";
        }

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
            return "redirect:/app/transaction/form?accNum=" + fromAcc.getAccNum();
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("refresh", "2;url=/app/transaction/form?accNum=" + fromAcc.getAccNum());
            throw e;
        }
    }
}

