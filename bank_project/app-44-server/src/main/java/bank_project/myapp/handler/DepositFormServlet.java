package bank_project.myapp.handler;

import bank_project.myapp.vo.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/account/deposit/form")
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

        // 간단한 CSS 추가
        out.println("<style>"
                + "body {font-family: Arial, sans-serif; margin:0 auto; width:50%; padding-top:50px;}"
                + "form {display:flex; flex-direction:column; gap:10px;} "
                + "input[type=submit] {cursor:pointer;} "
                + "</style>");

        out.println("<title>입금</title>");
        out.println("</head>");
        out.println("<body><form method='post' action='/transaction/deposit'>");
        out.printf("<input type='hidden' name='accNum' value='%s'> ", account.getAccNum());
        out.printf("계좌 번호: <strong>%s</strong> ", account.getAccNum());
        out.print("입금 금액:<br> <input type='number' name='amount'><br>");
        out.print("입금자명 :<br> <input type='customer' name='customer'><br>");
        out.print("<input type='submit' value='입금하기'>");
        out.print("</form></body></html>");

    }
}
