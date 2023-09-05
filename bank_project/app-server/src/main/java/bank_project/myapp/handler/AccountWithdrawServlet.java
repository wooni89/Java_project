package bank_project.myapp.handler;

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

@WebServlet("/account/withdraw")
public class AccountWithdrawServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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
            accountDao.withdraw(acc, amount);
            transactionDao.insert(transaction);
            sqlSessionFactory.openSession(false).commit();
            out.printf("%d원 출금 완료!</p>", amount);
            response.sendRedirect("list");

        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("error", e);
            request.setAttribute("message", e.getMessage());
            request.setAttribute("refresh", "1;url=list");
            request.getRequestDispatcher("/error").forward(request, response);
        }
    }
}




