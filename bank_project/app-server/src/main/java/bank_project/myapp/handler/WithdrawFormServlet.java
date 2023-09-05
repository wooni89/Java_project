package bank_project.myapp.handler;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/account/withdraw/form")
public class WithdrawFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");

        Account account = accountDao.findAccount(request.getParameter("accNum"));

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<style>"
                + "body {font-family: Arial, sans-serif; margin:0 auto; width:50%; padding-top:50px;}"
                + "form {display:flex; flex-direction:column; gap:10px;} "
                + "input[type=submit] {cursor:pointer;} "
                + "</style>");

        out.println("<title>출금</title>");
        out.println("</head>");
        out.println("<body><form method='post' action='/transaction/withdraw'>");

        request.getRequestDispatcher("/header").include(request, response);

        out.printf("<input type='hidden' name='accNum' value='%s'> ", account.getAccNum());
        out.printf("계좌 번호: <strong>%s</strong> ", account.getAccNum());
        out.print("출금 금액:<br> <input type='amount' name='amount'><br>");
        out.print("출금자명 :<br> <input type='customer' name='customer'><br>");
        out.print("<input type='submit' value='출금하기'>");

        request.getRequestDispatcher("/footer").include(request, response);

        out.println("</body>");
        out.println("</html>");
    }
}
