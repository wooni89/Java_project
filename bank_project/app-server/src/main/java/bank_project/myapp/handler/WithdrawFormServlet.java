package bank_project.myapp.handler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/transaction/withdraw/form")
public class WithdrawFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>출금</title>");
        out.println("</head>");

        out.println("<body><form method='post' action='/transaction/withdraw'>");
        out.println("계좌 번호: <input type='text' name='accNum'><br>");
        out.println("출금 금액: <input type='number' name='amount'><br>");
        out.print("<input type='submit' value='출금하기'>");
        out.print("</form></body></html>");

    }
}
