package bank_project.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bank_project.myapp.vo.Customer;

@WebServlet("/customer/detail")
public class CustomerDetailServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
   
    Customer tempCustom = new Customer();
    tempCustom.setEmail(request.getParameter("email"));
    tempCustom.setPassword(request.getParameter("password"));
    Customer customer = InitServlet.customerDao.findBy(tempCustom);
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원정보</h1>");
    
    if (customer == null) {
      out.println("<p>해당 회원이 없습니다!</p>");
      return;
    }
    
    out.println("<form action='/customer/update' method='post'>");
    out.println("<table border='1'>");
    out.printf("<tr><th style='width:120px;'>번호</th>"
        + " <td style='width:300px;'><input type='text' name='no' value='%d' readonly></td></tr>\n", customer.getNo());
    out.printf("<tr><th>이름</th>"
        + " <td><input type='name' name='name' value='%s'></td></tr>\n", customer.getName());
    out.printf("<tr><th>이메일</th>"
        + " <td><input type='email' name='email' value='%s'></td></tr>\n", customer.getEmail());
    out.println("<tr><th>암호</th>"
        + " <td><input type='password' name='password'></td></tr>");
    out.printf("<tr><th>연락처</th>"
        + " <td><input type='phoneNumber' name='phoneNumber' value='%s'></td></tr>\n", customer.getPhoneNumber());
    out.println("</table>");

    out.println("<div>");
    out.println("<button>변경</button>");
    out.println("<button type='reset'>초기화</button>");
    out.printf("<a href='/customer/delete?no=%d'>삭제</a>\n", customer.getNo());
    out.println("<a href='/customer/list'>목록</a>\n");
    out.println("</div>");
    out.println("</form>");
    
    out.println("</body>");
    out.println("</html>");
  }
}
