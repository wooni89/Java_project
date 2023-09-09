package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountDeleteController implements PageController {

    AccountDao accountDao;
    SqlSessionFactory sqlSessionFactory;

    public AccountDeleteController(AccountDao accountDao, SqlSessionFactory sqlSessionFactory) {
        this.accountDao = accountDao;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:../auth/login";
        }

        Account account = new Account();
        account.setAccNum(request.getParameter("accNum"));
        account.setOwner(loginUser);

        try {
            if (accountDao.delete(account) == 0) {
                throw new ServletException("해당 계좌의 소유주가 아니기에 권한이 없습니다");
            } else {
                response.sendRedirect("list");
            }
            sqlSessionFactory.openSession(false).commit();
            return "redirect:list";
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("refresh", "2;url=list");
            throw e;
        }
    }
}
