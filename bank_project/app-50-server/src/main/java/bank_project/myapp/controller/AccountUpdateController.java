package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountUpdateController implements PageController {

  AccountDao accountDao;
  SqlSessionFactory sqlSessionFactory;

  public AccountUpdateController(AccountDao accountDao, SqlSessionFactory sqlSessionFactory) {
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
    account.setNo(Integer.parseInt(request.getParameter("no")));
    account.setOwner(loginUser);
    account.setPassword(request.getParameter("password"));

    try {
      if (accountDao.update(account) == 0) {
        throw new Exception("회원이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        return "redirect:list";
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}
