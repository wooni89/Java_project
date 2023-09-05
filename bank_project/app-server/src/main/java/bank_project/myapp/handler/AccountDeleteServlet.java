package bank_project.myapp.handler;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;
import bank_project.util.NcpObjectStorageService;
import org.apache.ibatis.session.SqlSessionFactory;

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
            response.sendRedirect("/auth/form");
            return;
        }

        String accNum = request.getParameter("accNum");

        Account account = new Account();
        account.setAccNum(request.getParameter("accNum"));
        account.setOwner(loginUser);

        AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
        NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext().getAttribute("ncpObjectStorageService");

        try {
            if (accountDao.delete(account) == 0) {
                throw new ServletException("해당 계좌의 소유주가 아니기에 권한이 없습니다");
            } else {
                response.sendRedirect("list");
            }
            sqlSessionFactory.openSession(false).commit();
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("error", e);
            request.setAttribute("message", e.getMessage());
            request.setAttribute("refresh", "1;url=list");
            request.getRequestDispatcher("/error").forward(request, response);
        }


    }

}
