package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/transaction/form")
public class TransactionFormController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
            Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");

            Account account = accountDao.findByAccountAndOwner(loginUser.getNo());
            request.setAttribute("account", account);
            response.setContentType("text/html;charset=UTF-8");
            request.getRequestDispatcher("/WEB-INF/jsp/transaction/form.jsp").include(request, response);

        } catch (Exception e) {
            request.setAttribute("refresh", "1;url=/");
            throw new ServletException(e);
        }
    }
}
