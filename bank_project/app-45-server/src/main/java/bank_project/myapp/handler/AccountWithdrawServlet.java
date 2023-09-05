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

@WebServlet("/account/withdraw")
public class AccountWithdrawServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int amount = Integer.parseInt(request.getParameter("amount"));

        Account account = InitServlet.accountDao.findAccount(request.getParameter("accNum"));

        Account acc = new Account();
        acc.setAccNum(account.getAccNum());
        if (amount != 0) {
            acc.setBalance(account.getBalance() - amount);
        }

        Transaction transaction = new Transaction();
        transaction.setAcc_num(account);
        transaction.setTradeType("출금");
        transaction.setAmount(amount);
        transaction.setCustomer(request.getParameter("customer"));


        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            InitServlet.accountDao.withdraw(acc, amount);
            InitServlet.transactionDao.insert(transaction);
            InitServlet.sqlSessionFactory.openSession(false).commit();
            out.printf("%d원 출금 완료!</p>", amount);
            response.sendRedirect("list");

        } catch (Exception e) {
            InitServlet.sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("error", e);
            request.setAttribute("message", e.getMessage());
            request.setAttribute("refresh", "1;url=list");
            request.getRequestDispatcher("/error").forward(request, response);
        }
    }
}




