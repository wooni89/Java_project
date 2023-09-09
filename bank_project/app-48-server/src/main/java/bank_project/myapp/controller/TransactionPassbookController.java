package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.dao.TransactionDao;
import bank_project.myapp.vo.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/transaction/passbook")
public class TransactionPassbookController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
        TransactionDao transactionDao = (TransactionDao) this.getServletContext().getAttribute("transactionDao");
        Account account = accountDao.findAccount(request.getParameter("accNum"));
        request.setAttribute("account", account);
        request.setAttribute("list", transactionDao.findByAccountNumber(account));
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/WEB-INF/jsp/transaction/passbook.jsp").include(request, response);
    }
}