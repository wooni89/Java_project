package bank_project.myapp.handler;

import bank_project.myapp.vo.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/account/transfer/form")
public class TransferFormServlet extends HttpServlet {

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

        out.println("<style>"
                + "body {font-family: Arial, sans-serif; margin:0 auto; width:50%; padding-top:50px;}"
                + "form {display:flex; flex-direction:column; gap:10px;} "
                + "input[type=submit] {cursor:pointer;} "
                + "</style>");

        out.println("<title>송금</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<form method='post' action='/transaction/transfer'>");
        out.printf("<input type='hidden' name='fromAccNum' value='%s'> ", account.getAccNum());
        out.printf("계좌 번호: <strong>%s</strong> ", account.getAccNum());
        out.println("송금 금액: <input type='amount' name='amount'><br>");
        out.println("입금 계좌: <input type='toAccNum' name='toAccNum'><br>");
        out.print("<input type='submit' value='송금하기'>");
        out.print("</form>");
        out.print("</body></html>");

    }
}
