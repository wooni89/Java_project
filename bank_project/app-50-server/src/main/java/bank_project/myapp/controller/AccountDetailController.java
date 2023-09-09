package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountDetailController implements PageController {

  AccountDao accountDao;

  public AccountDetailController(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    request.setAttribute("account", accountDao.findAccount(request.getParameter("accNum")));
    return "/WEB-INF/jsp/account/detail.jsp";

  }
}

