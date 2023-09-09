package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.dao.TransactionDao;
import bank_project.myapp.vo.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TransactionPassbookController implements PageController {

    AccountDao accountDao;
    TransactionDao transactionDao;

    public TransactionPassbookController(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Account account = accountDao.findAccount(request.getParameter("accNum"));
        request.setAttribute("account", account);
        request.setAttribute("list", transactionDao.findByAccountNumber(account));
        return "/WEB-INF/jsp/transaction/passbook.jsp";
    }
}