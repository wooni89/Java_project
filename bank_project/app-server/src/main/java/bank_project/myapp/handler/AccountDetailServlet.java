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

@WebServlet("/account/detail")
public class AccountDetailServlet extends HttpServlet {

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
    out.println("<title>계좌정보</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>계좌 상세정보</h1>");
    if (account == null) {
      out.println("<p>해당 계좌가 없습니다!</p>");
      return;
    }

    out.println("<form action='/account/update' method='post'>");
    out.println("<table border='1'>");
    out.printf("<tr><th style='width:120px;'>번호</th>"
        + " <td style='width:300px;'><input type='text' name='no' value='%d' readonly></td></tr>\n",
        account.getNo());
    out.printf("<tr><th>이름</th> <td>%s</td></tr>\n", account.getOwner().getName());
    out.printf("<tr><th>계좌번호</th> <td pattern='[0-9]{3}-[0-9]{2}-[0-9]{3}'>%s</td></tr>\n", account.getAccNum());
    out.println("<tr><th>암호</th> <td><input type='password' name='password'></td></tr>");
    out.printf("<tr><th>계좌잔액</th><td>%d</td></tr>\n", account.getBalance());
    out.println("</table>");

    out.println("<div>");
    out.println("<button>변경</button>");
    out.println("<button type='reset'>초기화</button>");
    out.printf("<a href='/account/delete?accNum=%s'>삭제</a>\n", account.getAccNum());
    out.println("<a href='/account/list'>목록</a>\n");
    out.println("</div>");
    out.println("</form>");

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");
  }

}
