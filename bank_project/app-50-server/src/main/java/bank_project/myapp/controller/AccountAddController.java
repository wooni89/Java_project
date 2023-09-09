package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Customer;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountAddController implements PageController {

  AccountDao accountDao;
  SqlSessionFactory sqlSessionFactory;

  public AccountAddController(AccountDao accountDao, SqlSessionFactory sqlSessionFactory) {
    this.accountDao = accountDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
    return "/WEB-INF/jsp/account/form.jsp";
  }

    Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");

    if (loginUser == null) {
      return  "redirect:../auth/login";
    }

    Account account = new Account(); // 입력데이터 배열로 저장
    account.setOwner(loginUser);
    account.setAccNum(generateAccountNumber()); // Here we generate a new account number.
    account.setPassword(request.getParameter("password"));
    account.setBalance(0);


    try {
      accountDao.insert(account);
      sqlSessionFactory.openSession(false).commit();
      return  "redirect:list";

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }

  private String generateAccountNumber() {
    try {
      String maxAccNum = accountDao.findMaxAccNum();

      if (maxAccNum == null) {
        return "11100001";
      } else {
        int numberPart = Integer.parseInt(maxAccNum.substring(3));
        return "111" + String.format("%05d", numberPart + 1);
      }
    } catch (Exception e) {
      throw new RuntimeException("새 계좌를 발급할 수 없습니다.", e);
    }
  }
}
