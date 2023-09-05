package bank_project.myapp.handler;

import bank_project.myapp.vo.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/account/list")
public class AccountListServlet extends HttpServlet {

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
    out.println("<title>계좌</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>계좌 목록</h1>");
    out.println("<div style='margin:5px;'>");
    out.println("<a href='/account/form.html'>계좌 개설</a>");
    out.println("</div>");
    out.println("<table border='1'>");
    out.println("<thead>");
    out.println("  <tr> <th>번호</th> <th>계좌번호</th> <th>이름</th> <th>계좌잔액</th></tr>");
    out.println("</thead>");

    List<Account> list = InitServlet.accountDao.findAll();

    out.println("<tbody>");
    for (Account account : list) {
      out.printf("<tr>"
          + " <td>%s</td>"
          + " <td><a href='/account/detail?accNum=%s'>%s</a></td>"
          + " <td>%s</td>"
          + " <td>%s</td></tr>\n",
          account.getNo(),
          account.getAccNum(),
          account.getAccNum(),
          account.getOwner().getName(),
          account.getBalance());
    }

    out.println("</tbody>");
    out.println("</table>");
    out.println("<a href='/'>메인</a>");

    request.getRequestDispatcher("/footer").include(request, response);

    out.println("</body>");
    out.println("</html>");

  }

}
