package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.dao.TransactionDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Transaction;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TransactionDepositController implements PageController {

    AccountDao accountDao;
    TransactionDao transactionDao;
    SqlSessionFactory sqlSessionFactory;

    public TransactionDepositController(AccountDao accountDao, TransactionDao transactionDao, SqlSessionFactory sqlSessionFactory) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("GET")) {
            Account account = accountDao.findAccount(request.getParameter("accNum"));
            request.setAttribute("account", account);
            return "/WEB-INF/jsp/transaction/depositForm.jsp";
        }

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

        try {
            accountDao.deposit(acc, amount);
            transactionDao.insert(transaction);
            sqlSessionFactory.openSession(false).commit();
            return "redirect:/transaction/form?accNum=" + acc.getAccNum();
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("refresh", "2;url=/transaction/form?accNum=" + acc.getAccNum());
            throw e;
        }
    }
}
