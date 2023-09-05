package bank_project.myapp.handler;

import bank_project.myapp.vo.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/customer/detail")
public class CustomerDetailServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Customer customer = InitServlet.customerDao.findBy(Integer.parseInt(request.getParameter("no")));
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");

    request.getRequestDispatcher("/header").include(request, response);

    out.println("<h1>회원정보</h1>");
    if (customer == null) {
      out.println("<p>해당 회원이 없습니다!</p>");
      return;
    }
    
    out.println("<form action='/customer/update' method='post' enctype='multipart/form-data'>");
    out.println("<table border='1'>");
    out.printf("<tr><th style='width:120px;'>사진</th>"
            + " <td style='width:300px;'>"
            + (customer.getPhoto() == null ? "<img style='height:80px' src='/images/avatar.jpeg'>" :
            "<a href='https://kr.object.ncloudstorage.com/bank-bukit-1/customer/%s'>"
                    + "<img src='http://hfwoinxujgwq19373562.cdn.ntruss.com/customer/%1$s?type=f&w=60&h=80&faceopt=true&ttype=jpg'>"
                    + "</a>")
            + " <input type='file' name='photo'>"
            + "</td></tr>\n", customer.getPhoto());
    out.printf("<tr><th style='width:120px;'>번호</th>"
        + " <td style='width:300px;'><input type='text' name='no' value='%d' readonly></td></tr>\n", customer.getNo());
    out.printf("<tr><th>이름</th>"
        + " <td><input type='name' name='name' value='%s'></td></tr>\n", customer.getName());
    out.printf("<tr><th>이메일</th>"
        + " <td><input type='email' name='email' value='%s'></td></tr>\n", customer.getEmail());
    out.println("<tr><th>암호</th>"
        + " <td><input type='password' name='password'></td></tr>");
    out.printf("<tr><th>주소</th>"
        + " <td><input type='address' name='address' value='%s'></td></tr>\n", customer.getAddress());
    out.printf("<tr><th>성별</th>\n"
        + " <td><select name='gender'>\n"
        + " <option value='M' %s>남자</option>\n"
        + " <option value='W' %s>여자</option></select></td></tr>\n",
        (customer.getGender() == 'M' ? "selected" : ""),
        (customer.getGender() == 'W' ? "selected" : ""));
    out.printf("<tr><th>신용등급</th>"
            + " <td><input type='credit_rating' name='creditRating' value='%d'></td></tr>\n", customer.getCreditRating());
    out.println("</table>");

    out.println("<div>");
    out.println("<button>변경</button>");
    out.println("<button type='reset'>초기화</button>");
    out.printf("<a href='/customer/delete?no=%d'>삭제</a>\n", customer.getNo());
    out.println("<a href='/customer/list'>목록</a>\n");
    out.println("</div>");
    out.println("</form>");

    request.getRequestDispatcher("/footer").include(request, response);
    
    out.println("</body>");
    out.println("</html>");
  }
}
