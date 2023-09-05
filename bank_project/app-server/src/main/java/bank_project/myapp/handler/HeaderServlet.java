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

@WebServlet("/header")
public class HeaderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<div style='height:60px;background-color:white;'>");
        out.println("<img src='/images/logo.png' style='height:60px'>");
        out.println("<a href='/customer/list'>회원</a>");
        out.println("<a href='/account/list'>계좌업무</a>");
        if (loginUser != null) {
            Account account = InitServlet.accountDao.findByAccountAndOwner(loginUser.getNo());
            if (account != null) {
                out.printf("<a href='/transaction/form?accNum=%s'>은행업무 </a>", account.getAccNum());
            }
        }
        out.println("<a href='/board/list?category=1'>고객VOC</a>");
        out.println("<a href='/board/list?category=2'>공지사항</a>");

        if (loginUser == null) {
            out.println("<a href='/auth/form.html'>로그인</a>");
        } else {
            out.printf("%s %s <a href='/auth/logout'>로그아웃</a>\n",
                    (loginUser.getPhoto() == null ? "<img style='height:40px' src='/images/avatar.png'>" :
                            String.format("<img src='http://hfwoinxujgwq19373562.cdn.ntruss.com/customer/%s?type=f&w=30&h=40&faceopt=true&ttype=jpg'>",
                                    loginUser.getPhoto())),
                    loginUser.getName());
        }

        out.println("</div>");
    }
}
