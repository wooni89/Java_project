package bank_project.myapp.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bank_project.myapp.vo.Customer;

@WebServlet("/index.html")
public class HomeServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>은행 프로젝트</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>JW Bank11</h1>");
    out.println("<ul>");
    out.println("  <li><a href='/customer/list'>회원</a></li>");
    out.println("  <li><a href='/account/list'>계좌업무</a></li>");
    out.println("  <li><a href='/banking/deposit'>은행업무</a></li>");
    out.println("  <li><a href='/board/list?category=1'>고객VOC</a></li>");
    out.println("  <li><a href='/board/list?category=2'>공지사항</a></li>");

    Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");
    if (loginUser == null) {
      out.println("  <li><a href='/auth/form.html'>로그인</a></li>");
    } else {
      out.printf("  <li>%s <a href='/auth/logout'>로그아웃</a></li>", loginUser.getName());
    }
    out.println("</ul>");
    out.println("</body>");
    out.println("</html>");

  }
}

