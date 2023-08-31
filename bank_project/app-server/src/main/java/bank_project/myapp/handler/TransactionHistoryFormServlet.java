package bank_project.myapp.handler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/transaction/history/form")
public class TransactionHistoryFormServlet extends HttpServlet {

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
        out.println("<title>거래 내역 조회</title>");
        out.println("</head>");

        // Transaction history form
        out.print(
            "<body>"
                + "<form method=\"post\" action='/transaction/history'>"
                + "계좌 번호: <input type=\"text\" name=\"accNum\"><br>"
                + "<input type=\"submit\" value=\"조회하기\">"
                + "</form>"
                + "</body></html>"
        );
    }
}
