package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TransactionFormController implements PageController {

    AccountDao accountDao;

    public TransactionFormController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");

            Account account = accountDao.findByAccountAndOwner(loginUser.getNo());
            request.setAttribute("account", account);
            return "/WEB-INF/jsp/transaction/form.jsp";

        } catch (Exception e) {
            request.setAttribute("refresh", "1;url=/");
            throw e;
        }
    }
}
