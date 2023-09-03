package bank_project.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bank_project.myapp.vo.Account;

@WebServlet("/account/detail")
public class AccountDetailServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Account tempAccount = new Account();
    tempAccount.setAccNum(request.getParameter("accNum"));
    tempAccount.setPassword(request.getParameter("password"));
    Account account = InitServlet.accountDao.findAccountPassword(tempAccount);

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>계좌정보</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>계좌 상세정보</h1>");

    if (account == null) {
      out.println("<p>해당 회원이 없습니다!</p>");
      return;
    }

    out.println("<form action='/account/update' method='post'>");
    out.println("<table border='1'>");
    out.printf("<tr><th style='width:120px;'>번호</th>"
        + " <td style='width:300px;'><input type='text' name='no' value='%d' readonly></td></tr>\n",
        account.getNo());
    out.printf("<tr><th>이름</th>" + " <td><input type='text' name='name' value='%s'></td></tr>\n",
        account.getName());
    out.printf(
        "<tr><th>계좌번호</th>" + " <td><input type='text' name='accNum' value='%s'></td></tr>\n",
        account.getAccNum());
    out.println("<tr><th>암호</th>" + " <td><input type='text' name='password'></td></tr>");
    out.printf(
        "<tr><th>은행명</th>" + " <td><input type='text' name='bankName' value='%s'></td></tr>\n",
        account.getBankName());
    out.printf(
        "<tr><th>계좌잔액</th>" + " <td><input type='int' name='balance' value='%d'></td></tr>\n",
        account.getBalance());
    out.println("</table>");

    out.println("<div>");
    out.println("<button>변경</button>");
    out.println("<button type='reset'>초기화</button>");
    out.printf("<a href='/account/delete?accNum=%s'>삭제</a>\n", account.getAccNum());
    out.println("<a href='/account/list'>목록</a>\n");
    out.println("</div>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  }

}
