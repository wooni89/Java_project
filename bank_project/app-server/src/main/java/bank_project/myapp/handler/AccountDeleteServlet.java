package bank_project.myapp.handler;

import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account/delete")
public class AccountDeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect("/auth/form.html");
            return;
        }

        String accNum = request.getParameter("accNum");

        Account account = new Account();
        account.setAccNum(request.getParameter("accNum"));
        account.setOwner(loginUser);

        try {
            if (InitServlet.accountDao.delete(account) == 0) {
                throw new ServletException("해당 계좌의 소유주가 아니기에 권한이 없습니다");
            } else {
                response.sendRedirect("/account/list?accNum=" + accNum);
            }
            InitServlet.sqlSessionFactory.openSession(false).commit();
        } catch (Exception e) {
            InitServlet.sqlSessionFactory.openSession(false).rollback();
            throw new ServletException("해당하는 계좌번호와 비밀번호가 일치하는 계좌가 없습니다.");
        }


    }

}
