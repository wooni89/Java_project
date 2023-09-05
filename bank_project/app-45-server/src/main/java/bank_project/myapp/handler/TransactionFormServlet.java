package bank_project.myapp.handler;

import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/transaction/form")
public class TransactionFormServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect("/auth/form.html");
            return;
        }

        Account account = InitServlet.accountDao.findByAccountAndOwner(loginUser.getNo());

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>거래</title>");
        out.println("</head>");
        out.printf("<body>");

        request.getRequestDispatcher("/header").include(request, response);

        out.printf("<h2>계좌 번호: %s</h2>", account.getAccNum());
        out.printf("<h2>거래를 선택하세요:</h2>");
        out.printf("<ul>");
        out.printf("<li><a href='/account/deposit/form?accNum=%s'>입금</a></li>", account.getAccNum());
        out.printf("<li><a href='/account/withdraw/form?accNum=%s'>출금</a></li>", account.getAccNum());
        out.printf("<li><a href='/account/transfer/form?accNum=%s'>송금</a></li>", account.getAccNum());
        out.printf("<li><a href='/transaction/history?accNum=%s'>거래내역</a></li>", account.getAccNum());
        out.print("</ul>");

        // 메인으로 돌아가는 버튼 추가
        out.print("<br/><br/>");
        out.print("<button><a href='/'>메인으로 가기</button>");

        request.getRequestDispatcher("/footer").include(request, response);

        out.print("</body></html>");

    }
}
