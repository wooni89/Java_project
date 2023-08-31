package bank_project.myapp.handler;

import bank_project.myapp.vo.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/transaction/deposit/form")
public class DepositFormServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Account account = InitServlet.accountDao.findAccount(request.getParameter("accNum"));

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        //out.println("<meta http-equiv='refresh' content='1;url=/transaction/form'>");
        out.println("<title>입금</title>");
        out.println("</head>");
        out.println("<body><form method='post' action='/transaction/deposit'>");
        out.printf("계좌 번호: <td>%s</td>\n", account.getAccNum());
        out.println("입금 금액: <input type='number' name='amount'><br>");
        out.printf("계좌 비밀번호: <input type='password' name='password'><br>");
        out.print("<input type='submit' value='입금하기'>");
        out.print("</form></body></html>");

    }
}

