package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account/delete")
public class AccountDeleteController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            request.setAttribute("viewUrl", "redirect:../auth/login");
            return;
        }

        Account account = new Account();
        account.setAccNum(request.getParameter("accNum"));
        account.setOwner(loginUser);

        AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");

        try {
            if (accountDao.delete(account) == 0) {
                throw new ServletException("해당 계좌의 소유주가 아니기에 권한이 없습니다");
            } else {
                response.sendRedirect("list");
            }
            sqlSessionFactory.openSession(false).commit();
            request.setAttribute("viewUrl", "redirect:list");
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("refresh", "2;url=list");
            request.setAttribute("exception", e);
        }


    }

}
