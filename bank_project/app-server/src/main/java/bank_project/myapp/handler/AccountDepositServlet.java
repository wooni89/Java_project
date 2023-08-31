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

@WebServlet("/transaction/deposit")
public class AccountDepositServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int amount = Integer.parseInt(request.getParameter("amount"));

        Account account = new Account();
        account.setAccNum(request.getParameter("accNum"));
        account.setPassword(request.getParameter("password"));

        boolean verify = InitServlet.accountDao.findAccountAndPassword(account);

        if (!verify) {
            throw new ServletException("비밀번호가 일치하지 않습니다.");
        }

        if (amount != 0) {
            account.setBalance(account.getBalance() + amount);
        }

        Transaction transaction = new Transaction();
        transaction.setAcc_num(account);
        transaction.setTradeType("입금");
        transaction.setAmount(Integer.parseInt(request.getParameter("amount")));
        transaction.setCustomer(request.getParameter(account.getOwner().getName()));

        try {
            InitServlet.accountDao.update(account);
            InitServlet.transactionDao.deposit(transaction);
            InitServlet.sqlSessionFactory.openSession(false).commit();

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.printf("<p>%s 계좌로 %d원 입금 완료!</p>", account.getAccNum(), amount);

        } catch (Exception e) {
            InitServlet.sqlSessionFactory.openSession(false).rollback();
            throw new ServletException("입금을 처리하는데 실패하였습니다.", e);
        }
    }
}
