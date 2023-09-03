package bank_project.myapp.handler;

import bank_project.myapp.vo.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/customer/list")
public class CustomerListServlet extends HttpServlet {
  
  private static final long serialVersionUID = 1L;

  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 목록</h1>");
    out.println("<div style='margin:5px;'>");
    out.println("<a href='/customer/form.html'>새 회원</a>");
    out.println("</div>");
    out.println("<table border='1'>");
    out.println("<thead>");
    out.println("  <tr><th>번호</th> <th>이름</th> <th>성별</th> <th>이메일</th> <th>신용등급</th> </tr>");
    out.println("</thead>");

    List<Customer> list = InitServlet.customerDao.findAll();

    out.println("<tbody>");
    for (Customer cstm : list) {
      out.printf("<tr>"
          + " <td>%d</td>"
          + " <td><img src='http://hfwoinxujgwq19373562.cdn.ntruss.com/customer/%s?type=f&w=30&h=40&faceopt=true&ttype=jpg'>%s</td>"
          + " <td>%s</td>"
          + " <td><a href='/customer/detail?email=%s'>%s</a></td>"
          + " <td>%d</td></tr>\n",
           cstm.getNo(),cstm.getPhoto(), cstm.getName(),cstm.getGender() ,cstm.getEmail(), cstm.getEmail(), cstm.getCreditRating());
    }
    
    out.println("</tbody>");
    out.println("</table>");
    out.println("<a href='/'>메인</a>");
    out.println("</body>");
    out.println("</html>");
  }

}
