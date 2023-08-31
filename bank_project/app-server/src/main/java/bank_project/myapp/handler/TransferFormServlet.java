package bank_project.myapp.handler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/transaction/transfer/form")
public class TransferFormServlet extends HttpServlet {

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
        out.println("<title>송금</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<form method='post' action='/transaction/transfer'>");
        out.println("출금 계좌: <input type='text' name='fromAccNum'><br>");
        out.println("송금 금액: <input type='number' name='amount'><br>");

        out.println("입금 계좌: <input type='text' name='toAccNum'><br>");
        out.print("<input type='submit' value='송금하기'>");
        out.print("</form>");
        out.print("</body></html>");

    }
}
